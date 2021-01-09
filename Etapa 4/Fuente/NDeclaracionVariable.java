

public class NDeclaracionVariable  extends NSentencia{
	//atributos
	protected Tipo tipo;
	protected String nombre;
	protected NBloque bloque;
	
	//constrcutor
	public NDeclaracionVariable(Token token, Tipo tipo, String nombre, NBloque bloque){
		super(token);
		this.tipo = tipo;
		this.nombre = nombre;
		this.bloque = bloque;
	}

	//getters
	public Tipo getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}
	

	//chequear
	public void chequear() throws ExceptionSemanticoChequeo{
		//metodo actual
		EntradaConParams metActual = Principal.ts.getMetodoActual();
		
		//verifico que no exista una variable con ese nombre
		if(!metActual.estaAtr(nombre)){
			
			//creo la variable local
			EntradaVarMetodo var = new EntradaVarLocal(tipo,token);
			
			//la agrego a la lista de variables del bloque
			bloque.getVariables().put(nombre,var);
			
			//la agrego a las variables del metodo
			metActual.getVariablesMetodo().put(nombre, var);		
			
		//ya exixte una variable con ese nombre	
		}else{
			throw new ExceptionAtributoExiste(nombre,token.getLinea(),token.getColumna());
		}
		
	}
	
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Declaracion variable de bloque "+bloque);
		tabs(n+1); System.out.println("   Tipo: "+tipo.nombre+"");
		tabs(n+1); System.out.println("   Nombre: "+nombre+"");
	}



	

	
	
}
