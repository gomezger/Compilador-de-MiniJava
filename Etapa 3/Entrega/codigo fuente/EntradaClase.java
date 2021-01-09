

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;


public class EntradaClase extends Entrada{
	protected String herencia;
	protected HashMap<String,EntradaVariable> atributos;
	protected HashMap<String,EntradaConParams> metodos;
	protected HashMap<Integer,EntradaConParams> constructores; 
	protected boolean consolidado;
	
	/**
	 * constructor
	 * @param nombre Nombre de la clase
	 */
	public EntradaClase(Token token){
		super(token);
		this.herencia = null;
		this.atributos = new HashMap<String,EntradaVariable>();
		this.metodos = new HashMap<String,EntradaConParams>();
		this.constructores = new HashMap<Integer,EntradaConParams>();
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
	public HashMap<String,EntradaVariable> getAtributos(){
		return this.atributos;
	}
	public HashMap<Integer, EntradaConParams> getConstructores(){
		return this.constructores;
	}
	public HashMap<String, EntradaConParams> getMetodos(){
		return this.metodos;
	}
	
	
	
	public void consolidar(HashMap<String,EntradaClase> clases) throws ExceptionSemanticoDeclaracion {
		//System.out.println("QUERIO CONSOLIDAR "+this.getNombre());
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
		//System.out.println("CONSOLIDE "+this.getNombre());
		this.consolidado = true;
	}
	
	
	private void consolidarAtributos(EntradaClase padre) throws ExceptionSemanticoDeclaracion{
		for(Entry<String, EntradaVariable> atributo: padre.getAtributos().entrySet()){
			
			//agrego los atributos del padre al hijo
			//verifico si ese atributo no existe en la clase hijo
			if(!this.atributos.containsKey(atributo.getKey()))
				this.atributos.put(atributo.getKey(),atributo.getValue());
			else{
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
				if(!this.metodos.containsKey(metPadre.getKey()))
					
					//no existe asi q lo agrego a la clase sin verificar nada			
					this.metodos.put(metPadre.getKey(),metPadre.getValue());
				
				
				//existe un metodo con ese nombre y cantidad de parametros
				else{
					
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
	public void addAtributo(EntradaVariable a) throws ExceptionSemanticoDeclaracion{
		//no esta el atributo en la clase y lo agrego
		if(!atributos.containsKey(a.getNombre())){
			
			atributos.put(a.getNombre(),a);
		
		//error, ya existe el atributo 
		}else{
			throw new ExceptionAtributo(a.getNombre(),a.getLinea(),a.getColumna());
		}
	}
	
	public void addAtributos(LinkedList<EntradaVariable> atrs) throws ExceptionSemanticoDeclaracion{
		for(EntradaVariable a: atrs){
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
		
		//error, ya existe el metodo con esa cant de params
		}else{
			throw new ExceptionMetodo(met.getNombre(),met.getCantParams(),met.getLinea(),met.getColumna());
		}
	}
	
	public void imprimir(){
		System.out.println("#Atributos: ");
		for(Map.Entry<String,EntradaVariable> entry: atributos.entrySet()) {
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



	
	
	
	
}
