

public class NAccesoVar extends NAcceso {
	//atributos
	public EntradaVariable var;
	
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
			this.var = metActual.buscarAtr(token);
			
			//verifica que no sea null
			if(var!=null){
				
				//verifico que si el metodo es estatico y la variable es de isntancia, hay error (ver si se redefinio la variable)
				if(!metActual.esDinamico() && metActual.getClase().getAtributos().containsKey(varNombre)
						&& !Principal.ts.getMetodoActual().getVariablesMetodo().containsKey(varNombre)){
					
					throw new ExceptionMetodoEstatico(varNombre,metActual.getNombre(),token.getLinea(),token.getColumna());
				
					
				}else{
				
					//verifico que la variable sea del padre y privada
					if(Principal.ts.getClaseActual().getAtributos().containsKey(token.getLexema())){
						
						//obtengo el atr
						EntradaAtributo atr = Principal.ts.getClaseActual().getAtributos().get(token.getLexema());
						
						
						
						//es privado
						if(!atr.esPublico()){
							
							//me fijo si esta en el padre, tiene q fallar
							EntradaClase padre = Principal.ts.getClase(Principal.ts.getClaseActual().getHerencia());
							
							//esta en el padre, falla
							if(padre.getAtributos().containsKey(token.getLexema())){
								
								throw new ExceptionSemanticoChequeo("El atributo "+token.getLexema()+" no es visible en la clase", token.getLinea(), token.getColumna());
							}
							
						}
						
					}
					
					
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
	
	
	
	public void generar() {
		
		//me fijo si es atr de instancia
		if(var instanceof EntradaAtributo){
			
			//cargo el this
			Principal.gen.gen("LOAD 3", "Cargo el this");
			
			//si no es lado izquierdo (es derecho) o tengo encadenado
			if(!li || !(enc instanceof NEncadenadoVacio)){
				Principal.gen.gen("LOADREF "+var.offset, "Cargo el atributo");
			//es lado izquierdo y no encadenado vacio
			}else{
				Principal.gen.gen("SWAP", "Intercambio valores de la pila");
				Principal.gen.gen("STOREREF "+var.offset, "Guardo valor en la pila");
			}
		
		//es una variable local o param
		}else{
			
			//si no es lado izquierdo o tengo encadenado
			if(!li || !(enc instanceof NEncadenadoVacio)){
				Principal.gen.gen("LOAD "+var.offset, "Cargo var local/param");
			
			//es lado drecho y tiene encadenado vacio
			}else{
				
				Principal.gen.gen("STORE "+var.offset, "Guardo var local/ param");
			}
			
		}	
		//genero el encadenado
		enc.generar();
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
