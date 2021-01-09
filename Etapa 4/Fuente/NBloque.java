

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class NBloque  extends NSentencia{
	//atributos
	protected LinkedList<NSentencia> lista;
	public NBloque padre;
	protected EntradaConParams unidad;
	protected HashMap<String, EntradaVarMetodo> variables;
	
	//constrcutor
	public NBloque(Token token, LinkedList<NSentencia> lista, NBloque padre, EntradaConParams unidad){
		super(token);
		this.lista = lista;
		this.padre = padre;
		this.variables = new HashMap<String, EntradaVarMetodo>();
		this.unidad = unidad;
	}
	
	public NBloque(Token token, NBloque padre, EntradaConParams unidad){
		super(token);
		this.padre = padre;
		this.variables = new HashMap<String, EntradaVarMetodo>();
		this.unidad = unidad;
	}
	
	//setters
	public void setSentencias(LinkedList<NSentencia> lista){
		this.lista = lista;
	}
	public void setUnidad(EntradaConParams unidad){
		this.unidad = unidad;
	}
	
	//getters
	public NBloque getPadre() {
		return padre;
	}
	public LinkedList<NSentencia> getLista() {
		return lista;
	}	
	public HashMap<String, EntradaVarMetodo> getVariables() {
		return variables;
	}

	//chequear
	public void chequear() throws ExceptionSemantico{
		//seteo bloque actual
		Principal.ts.bloqueActual = this;		
		
		
			//recorro todas las senteicas del bloque
			for(NSentencia s: lista)
				try{
					s.chequear();		
				}catch(Exception e){
					Principal.ts.setHayErrores();
					
					if(!Principal.ts.errores_detalles.contains(e.getMessage())){
						Principal.ts.errores_detalles.add(e.getMessage());
						System.out.println(e.getMessage());
					}
				}
		
		//elimino las variables definidas en este bloque
		for(Entry<String,EntradaVarMetodo> var: this.variables.entrySet())
			unidad.getVariablesMetodo().remove(var.getKey());

		//seteo bloque actual
		Principal.ts.bloqueActual = padre;		
	}
	

	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Bloque ("+this+") hijo de ("+padre+")");
		for(NSentencia s: lista)
			s.imprimir(n+1);
	}

	
}
