

public class NAccesoVar extends NAcceso {
	//atributos
	
	//constructor
	public NAccesoVar(Token token, NEncadenado enc, boolean li, boolean at){
		super(token,enc,li,at);
	}
	
	
	
	//chequear
	public TipoBase chequear() throws ExceptionSemantico{
		
		//verificar si se quiere usar un this en asignacion inline de atributo instancia
		if(!this.at){
		
			String varNombre = token.getLexema();
			EntradaConParams metActual = Principal.ts.getMetodoActual();
			EntradaVariable var = metActual.buscarAtr(token);
			
			//verifica que no sea null
			if(var!=null){
				
				//verifico que si el metodo es estatico y la variable es de isntancia, hay error (ver si se redefinio la variable)
				if(!metActual.esDinamico() && metActual.getClase().getAtributos().containsKey(varNombre)
						&& !Principal.ts.getMetodoActual().getVariablesMetodo().containsKey(varNombre)){
					
					throw new ExceptionMetodoEstatico(varNombre,metActual.getNombre(),token.getLinea(),token.getColumna());
				
					
				}else{
				
					//pido el tipo de la variable
					TipoBase tipoVar = var.getTipo();
					
					//le paso a encadenado el tipo de la var y me retorna un tipo
					return enc.chequear(tipoVar);
			
				}
				
			}else{
				
				//no existe la variable
				throw new ExceptionAtributoNoExiste(varNombre,metActual.getNombre(),token.getLinea(),token.getColumna());
			}
		//se quiere usar this en atributo
		}else{
			throw new ExceptionSemanticoChequeo("No se puede usar una variable en una asignación inline de un atributo de instancia. ",token.getLinea(),token.getColumna());
		}
	}
	
	//imprimir
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Acceso Var");
		tabs(n+1); System.out.println("Nombre: "+token.getLexema()+"");
		tabs(n+1); System.out.println("Lado Izquierdo: "+li+"");
		enc.imprimir(n+1);
	}

	
}
