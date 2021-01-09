


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;


public class EntradaClase extends Entrada{
	protected String herencia;
	protected HashMap<String,EntradaAtributo> atributos;
	protected HashMap<String,EntradaConParams> metodos;
	protected HashMap<Integer,EntradaConParams> constructores; 
	public LinkedList<EntradaConParams> metodosDin;
	public LinkedList<EntradaConParams> metodosEst;
	protected boolean consolidado;
	public int offsetActualMet = 0;
	public int offsetActualAtr = 1;
	
	/**
	 * constructor
	 * @param nombre Nombre de la clase
	 */
	public EntradaClase(Token token){
		super(token);
		this.herencia = null;
		this.atributos = new HashMap<String,EntradaAtributo>();
		this.metodos = new HashMap<String,EntradaConParams>();
		this.constructores = new HashMap<Integer,EntradaConParams>();
		this.metodosDin = new LinkedList<EntradaConParams>();
		this.metodosEst = new LinkedList<EntradaConParams>();
		this.consolidado = false;
	}
	
	
	
	//ggeters
	public String getLexema(){
		return this.token.getLexema();
	}	
	public String getHerencia(){
		return this.herencia;
	}
	public int getCantConstructores(){
		return this.constructores.size();
	}
	public HashMap<String,EntradaAtributo> getAtributos(){
		return this.atributos;
	}
	public HashMap<Integer, EntradaConParams> getConstructores(){
		return this.constructores;
	}
	public HashMap<String, EntradaConParams> getMetodos(){
		return this.metodos;
	}
		
	//generar
	public void generar(){
		
		
		
		
		//genero los datos de la tabla
		Principal.gen.gen(".DATA","DATOS CLASE "+this.getNombre());
		Principal.gen.gen("VT_"+this.getNombre()+": ", "VT de la clase "+this.getNombre());
		
		//para saber si tiene un metodo dinamico
		boolean hayDinamico = false;		
		
		//agrego los metodos a la VT
		for(Entry<String, EntradaConParams> entryMetodo: this.metodos.entrySet()){
			
			
			EntradaConParams met = entryMetodo.getValue();
			
			//si es dinamico lo agrego a la VT
			if(met.esDinamico()){
				Principal.gen.gen_dw("DW "+met.getEtiqueta(),"",met.offset);
				hayDinamico = true;
			}
		}
		
		//agrego la VT a la clase con los metodo ordenados
		Principal.gen.agregar_dw();
		
		
		//si no hay dinamico, hay que poner noop por VT vacio
		if(!hayDinamico){
			Principal.gen.gen("NOP", "No hay metodo dinamicos");
		}
		
		//empiezo a imprimir codigo
		Principal.gen.gen(".CODE", "CODE CLASE "+this.getNombre());
		
		//generar todo el codigo de todos los metodos
		for(Entry<String, EntradaConParams> entryMetodo: this.metodos.entrySet()){
			EntradaConParams met = entryMetodo.getValue();
			
			//seteo metodo actual
			Principal.ts.metodoActual = met; 
			
			//genero el codigo del metodo
			met.generar();
		}
		
		//generar todo el codigo de todos los metodos
		for(Entry<Integer, EntradaConParams> entryCtor: this.constructores.entrySet()){
			EntradaConParams met = entryCtor.getValue();
			
			//seteo metodo actual
			Principal.ts.metodoActual = met; 
			
			//genero el codigo del metodo
			met.generar();
		}
		
	}
	
	
	//consolidar metodos de la clase
	public void consolidar(HashMap<String,EntradaClase> clases) throws ExceptionSemanticoDeclaracion {
		//si no estoy consolidado, tengo q consolidar
		if(!this.consolidado){
			
			//si mi padre no esta consolidado, le pido q se consolide
			EntradaClase padre = clases.get(this.getHerencia());
			if(!padre.consolidado){
				
				padre.consolidar(clases);
				
			}
			
			/**el padre ya esta consolidado*/
			//consolido los atributos
			this.consolidarAtributos(padre);
			//consolido los metodos
			this.consolidarMetodos(padre);
			
		}	
		this.consolidado = true;
	}
	
	
	private void consolidarAtributos(EntradaClase padre) throws ExceptionSemanticoDeclaracion{
		for(Entry<String, EntradaAtributo> atributo: padre.getAtributos().entrySet()){
			
			//agrego los atributos del padre al hijo
			//verifico si ese atributo no existe en la clase hijo
			if(!this.atributos.containsKey(atributo.getKey())){
				this.atributos.put(atributo.getKey(),atributo.getValue());
				this.offsetActualAtr++;
			}else{
				try{
					throw new ExceptionAtributoRedefinido(atributo.getKey(),atributo.getValue().getLinea(),atributo.getValue().getColumna());
				}catch(ExceptionAtributoRedefinido e){
					System.out.println(e.getMessage());
					Principal.ts.setHayErrores();
				}
			}
		}
	}
	
	
	private void consolidarMetodos(EntradaClase padre) throws ExceptionSemanticoDeclaracion{
		//recorro los metodo de la clase padre
		for(Entry<String, EntradaConParams>  metPadre: padre.getMetodos().entrySet()){			
			
			try{
				
				//agrego los metodos del padre al hijo
				//verifico si ese metodo no existe en la clase hijo
				if(!this.metodos.containsKey(metPadre.getKey())){
					
					//no existe asi q lo agrego a la clase sin verificar nada			
					this.metodos.put(metPadre.getKey(),metPadre.getValue());
				
					//sumo el offset de la clase si es dinamico
					if(metPadre.getValue().esDinamico())
						this.offsetActualMet++;
				
				
				//existe un metodo con ese nombre y cantidad de parametros
				}else{
					
					EntradaMetodo metHijo = (EntradaMetodo) this.metodos.get(metPadre.getKey());
					
					//verifico que los tipo de retorno sean iguales
					if(metPadre.getValue().getTipo().getNombre().equals(metHijo.getTipo().getNombre())){
					
						//verifico que si los dos son dinamicos o estaticos
						if(metPadre.getValue().esDinamico()==metHijo.esDinamico()){
						
							//recorro los parametros del hijo y los comparo con los del padre
							for(EntradaParametro paramHijo: metHijo.getListaParametros()){
																
								//recupero el parametro del padre de misma posicion que paramHijo
								EntradaParametro paramPadre = metPadre.getValue().getListaParametros().get(paramHijo.pos-1);
								
								
								if(!paramPadre.getTipo().getNombre().equals(paramHijo.getTipo().getNombre())){
									throw new ExceptionMetodoRedefinido(paramHijo.getLinea(),paramHijo.getColumna());
								}
									
								//como redefino el metodo, tiene el mismo offset
								//metHijo.offset = metPadre.getValue().offset;
								
							}
						
						// uno es estaitco y el otro es dinamico
						}else{								
							throw new ExceptionMetodoRedefinido(metHijo.getLinea(),metHijo.getColumna());					
						}
					}else{
						throw new ExceptionMetodoRedefinido(metHijo.getLinea(),metHijo.getColumna());
					}
					
					
				}
			}catch(ExceptionMetodoRedefinido e){
				System.out.println(e.getMessage());
				Principal.ts.setHayErrores();				
			}
			
			
			
		}
		
		//asigno offset para los atr de la clase
		for(Entry<String,EntradaAtributo> atr: this.atributos.entrySet()){
			
			//verificar que en el padre existe un metodo igual y sea una redefinicion
			if(Principal.ts.getClase(this.getHerencia()).getAtributos().containsKey(atr.getValue().getNombre())){
				
				//pongo el offset del padre
				EntradaAtributo atrPadre = Principal.ts.getClase(this.getHerencia()).getAtributos().get(atr.getValue().getNombre());
				atr.getValue().offset = atrPadre.offset;						
						
			
			//no es una redefinicion
			}else{
				
				//asigno offset al metodo
				atr.getValue().offset = this.offsetActualAtr;	
				
				//sumo offset a hijo
				this.offsetActualAtr++;	
			}

		}
		
		
		//pongo offset a parametros
		for(Entry<String,EntradaConParams> met: this.metodos.entrySet()){
			//me fijo si tiene this
			int cons;
			if(met.getValue().esDinamico())
				cons = 3;
			else
				cons = 2;
			
			//posicion del param
			int i = 0;
			for(EntradaParametro param: met.getValue().getListaParametros()){
				param.offset = cons+met.getValue().getCantParams()-i++;
			}
			
		}
		//pongo offset a parametros
		for(Entry<Integer, EntradaConParams> met: this.constructores.entrySet()){
			//me fijo si tiene this
			int cons;
			if(met.getValue().esDinamico())
				cons = 3;
			else
				cons = 2;
			
			//posicion del param
			int i = 0;
			for(EntradaParametro param: met.getValue().getListaParametros()){
				param.offset = cons+met.getValue().getCantParams()-i++;
			}
			
		}
				
		//asigno offset para los metodos de la clase
		for(EntradaConParams metDin: this.metodosDin){
			
			//verificar que en el padre existe un metodo igual y sea una redefinicion
			if(Principal.ts.getClase(metDin.getClase().getHerencia()).getMetodos().containsKey(metDin.getNombre()+"$"+metDin.getCantParams())){
				
				//pongo el offset del padre
				EntradaConParams metPadre = Principal.ts.getClase(metDin.getClase().getHerencia()).getMetodos().get(metDin.getNombre()+"$"+metDin.getCantParams());
				metDin.offset = metPadre.offset;						
						
			
			//no es una redefinicion
			}else{
				
				//asigno offset al metodo
				metDin.offset = metDin.getClase().offsetActualMet;	
				
				//sumo offset a hijo
				metDin.getClase().offsetActualMet++;	
			}

		}
		
	}
	
	
	//setters
	public void setConsolidado(boolean consolidado){
		this.consolidado = consolidado;
	}
	public void setHerencia(Token token){
		this.herencia = token.getLexema();
	}
	public void setHerenciaObject(){
		this.herencia = "Object";
	}	
	public void addAtributo(EntradaAtributo a) throws ExceptionSemanticoDeclaracion{
		//no esta el atributo en la clase y lo agrego
		if(!atributos.containsKey(a.getNombre())){
			
			atributos.put(a.getNombre(),a);
		
		//error, ya existe el atributo 
		}else{
			throw new ExceptionAtributo(a.getNombre(),a.getLinea(),a.getColumna());
		}
	}
	
	public void addAtributos(LinkedList<EntradaAtributo> atrs) throws ExceptionSemanticoDeclaracion{
		for(EntradaAtributo a: atrs){
			this.addAtributo(a);
		}
	}
	
	public void addConstructor(EntradaConParams ctor) throws ExceptionSemanticoDeclaracion{
		
		//veririco q el nomnre coincida con la clase
		if(ctor.getNombre().equals(this.getNombre())){
		
			//no esta el atributo en la clase y lo agrego
			if(!constructores.containsKey(ctor.getCantParams())){
				
				constructores.put(ctor.getCantParams(),ctor);
			
			//error, ya existe el cons 
			}else{
				throw new ExceptionConstructor(ctor.getCantParams(),ctor.getLinea(),ctor.getColumna());
			}
		}else{
			throw new ExceptionConstructorMalo(ctor.getNombre(),this.getNombre(),ctor.getLinea(),ctor.getColumna());			
		}
	}
	
	public void addMetodo(EntradaConParams met) throws ExceptionMetodo{

		//creo el string con nombre y cantidad de parametros separados por $
		String key = met.getNombre()+"$"+met.getCantParams();
		
		//no esta el metodo con esa cantidad de parametros y lo agrego
		if(!metodos.containsKey(key)){
			
			metodos.put(key,met);
			
			
			//agregar a lista
			if(met.esDinamico())
				metodosDin.add(met);
			//es estatico
			else
				metodosEst.add(met);
		
		//error, ya existe el metodo con esa cant de params
		}else{
			throw new ExceptionMetodo(met.getNombre(),met.getCantParams(),met.getLinea(),met.getColumna());
		}
	}
	
	public void imprimir(){
		System.out.println("#Atributos: ");
		for(Map.Entry<String,EntradaAtributo> entry: atributos.entrySet()) {
			System.out.print("+ ");
			entry.getValue().imprimir();
			System.out.println("");
		}
		System.out.println("");
		System.out.println("#Constructores: ");
		for(Map.Entry<Integer, EntradaConParams> entry: constructores.entrySet()) {
			entry.getValue().imprimir();
		}
		System.out.println("");
		
		System.out.println("#Metodos: ");
		for(Map.Entry<String, EntradaConParams> entry: metodos.entrySet()) {
			entry.getValue().imprimir();
		}
		System.out.println("");
	}


	public void imprimirAST(){
		System.out.println("#Atributos: ");
		for(Map.Entry<String,EntradaAtributo> entry: atributos.entrySet()) {
			System.out.print("+ ");
			entry.getValue().imprimirAST();
			System.out.println("");
		}
		System.out.println("");
		System.out.println("#Constructores: ");
		for(Map.Entry<Integer, EntradaConParams> entry: constructores.entrySet()) {
			entry.getValue().imprimirAST();
		}
		System.out.println("");
		
		System.out.println("#Metodos: ");
		for(Map.Entry<String, EntradaConParams> entry: metodos.entrySet()) {
			entry.getValue().imprimirAST();
		}
		System.out.println("");
	}


	
	
	
	
}
