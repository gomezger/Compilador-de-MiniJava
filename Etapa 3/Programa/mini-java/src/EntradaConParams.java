

import java.util.HashMap;
import java.util.LinkedList;


public abstract class EntradaConParams extends Entrada {
	//atributos
	protected HashMap<String, EntradaParametro> params;
	protected LinkedList<EntradaParametro> listaParams;
	protected EntradaClase clase;
	protected TipoBase tipoRetorno;
	
	//constrcutor
	protected EntradaConParams(Token token, EntradaClase clase, TipoBase tipo){
		super(token);
		this.tipoRetorno = tipo;
		this.params = new HashMap<String, EntradaParametro>();
		this.listaParams = new LinkedList<EntradaParametro>();
		this.clase = clase;
	}
	
	//setters
	public void addParam(EntradaParametro parametro) throws ExceptionSemanticoDeclaracion{

		//seteo la posicion
		parametro.setPos(this.getCantParams()+1);
		
		
		//no esta el atributo en la clase y lo agrego
		if(!params.containsKey(parametro.getNombre())){
			
			params.put(parametro.getNombre(),parametro);
			listaParams.add(parametro);
			
		//error, ya existe el parametro 
		}else{
			throw new ExceptionParametro(parametro.getNombre(),token.getLinea(),token.getColumna());
		}
	}

	
	//getters
	public HashMap<String,EntradaParametro> getParametros(){
		return this.params;
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
		return false;
	}

	public void imprimir() {		
	}
}
