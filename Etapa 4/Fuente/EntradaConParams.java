


import java.util.HashMap;
import java.util.LinkedList;


public abstract class EntradaConParams extends Entrada {
	//atributos
	protected HashMap<String, EntradaParametro> params;
	protected LinkedList<EntradaParametro> listaParams;
	protected EntradaClase clase;
	protected TipoBase tipoRetorno;
	
	//para el chequeo de declaraciones
	protected NBloque bloque;
	public HashMap<String, EntradaVarMetodo> locales;
	
	//constructor
	protected EntradaConParams(Token token, EntradaClase clase, TipoBase tipo){
		super(token);
		this.tipoRetorno = tipo;
		this.params = new HashMap<String, EntradaParametro>();
		this.listaParams = new LinkedList<EntradaParametro>();
		this.clase = clase;
		this.locales = new HashMap<String, EntradaVarMetodo>();
	}
	
	//setters
	public void setBloque(NBloque bloque){
		this.bloque = bloque;
	}
	
	public void addParam(EntradaParametro parametro) throws ExceptionSemanticoDeclaracion{

		//seteo la posicion
		parametro.setPos(this.getCantParams()+1);
		
		
		//no esta el atributo en la clase y lo agrego
		if(!params.containsKey(parametro.getNombre())){
			
			params.put(parametro.getNombre(), parametro);
			locales.put(parametro.getNombre(), parametro);
			listaParams.add(parametro);
			
		//error, ya existe el parametro 
		}else{
			throw new ExceptionParametro(parametro.getNombre(),token.getLinea(),token.getColumna());
		}
	}

	
	//getters
	public void chequear() throws ExceptionSemantico{
		if(this.bloque!=null) 
			this.bloque.chequear();
	}
	public NBloque getNodoBloque(){
		return this.bloque;
	}
	public HashMap<String,EntradaParametro> getParametros(){
		return this.params;
	}
	public HashMap<String,EntradaVarMetodo> getVariablesMetodo(){
		return this.locales;
	}
	public int getCantParams(){
		return this.params.size();
	}
	public LinkedList<EntradaParametro> getListaParametros(){
		return this.listaParams;
	}
	public TipoBase getTipo(){
		return this.tipoRetorno;
	}
	public boolean esDinamico() {
		return true;
	}
	public EntradaClase getClase(){
		return this.clase;
	}
	public abstract boolean esConstructor();
	
	/**
	 * retorna true si en las variable locales existe una variable con ese nombre
	 * @param nombre
	 * @return
	 */
	public boolean estaAtr(String nombre){
		return locales.containsKey(nombre);
	}
	
	/**
	 * Retorna la entradaVariable con clave nombre
	 * @param nombre
	 * @return
	 * @throws ExceptionAtributoNoExiste
	 */
	public EntradaVariable buscarAtr(Token var) throws ExceptionAtributoNoExiste{
		//esta en las variables locales
		if(locales.containsKey(var.getLexema()))
			return locales.get(var.getLexema());
		
		//esta en las varaibles de isntancia
		else if(clase.getAtributos().containsKey(var.getLexema()))
			return clase.getAtributos().get(var.getLexema());
		
		//no existe la variable
		else
			throw new ExceptionAtributoNoExiste(var.getLexema(),getNombre(),var.getLinea(),var.getColumna());
	}
	

	
	//imprimir
	public void imprimirAST() {	
		if(bloque!=null) this.bloque.imprimir(0);
	}
	public void imprimir() {	
	}
	
	
	
}
