package etapa4AST;

import etapa1.Principal;
import etapa1.Token;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoArreglo;
import etapa3Tipos.TipoArregloBoolean;
import etapa3Tipos.TipoArregloChar;
import etapa3Tipos.TipoArregloInt;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoInt;
import etapa4Exp.ExceptionSemanticoChequeo;
import etapa4Exp.ExceptionTipoExpresion;

public class NAccesoArregloEncadenado extends NEncadenado {
	//atributos
	protected NExpresion exp;
	protected NEncadenado enc;
	protected boolean li;
	
	
	//constrcutor
	public NAccesoArregloEncadenado(Token token, NExpresion exp, NEncadenado enc, boolean li){
		super(token);
		this.exp = exp;
		this.enc = enc;
		this.li = li;
	}
	

	//getters
	public NExpresion getExp() {
		return exp;
	}
	public NEncadenado getEnc() {
		return enc;
	}
	
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Acceso Arreglo Encadenado");
		tabs(n+1); System.out.println("Lado Izq.: "+li); 
		exp.imprimir(n+1);
		enc.imprimir(n+1);
	}



	public TipoBase chequear(TipoBase tipo) throws ExceptionSemantico {
		//verificar si es de tipo arreglo la variable que estaba a la izqueirda
		if(tipo instanceof TipoArregloChar || tipo instanceof TipoArregloInt || tipo instanceof TipoArregloBoolean){
			
			//verifico que exp sea de tipo int
			TipoBase tipoExp = this.exp.chequear();			
			if(tipoExp.esCompatible(new TipoInt())){
				
				//no puede haber encadenados en un arreglo primitivo
				if(enc instanceof NEncadenadoVacio){
					
					return ((TipoArreglo)tipo).getTipoPrimitivo();	
					
				}else{
					
					throw new ExceptionSemanticoChequeo("En un arreglo no puede haber encadenado, ya que solo se trabaja con tipos primitivos",token.getLinea(),token.getColumna());
				}
				
			
			}else{
				throw new ExceptionTipoExpresion("int",tipoExp.getNombre(),exp.getToken().getLinea(),exp.getToken().getColumna());
			}			
			
			
		// si tenemos a[5], puede que a no sea de tipo arreglo	
		}else{
			throw new ExceptionSemanticoChequeo("El tipo "+tipo.getNombre()+" no es un arreglo. ",token.getLinea(),token.getColumna());
		}
	}


	public void generar() {
		
		//estoy de una lado izquierdo, asi que tengo que guardar lo que esta en la pila
		if(li){
			exp.generar();
			Principal.gen.gen("ADD", "Muevo el puntero del arreglo");
			Principal.gen.gen("SWAP", "Intercambio valores de la pila");
			Principal.gen.gen("STOREREF 0", "Guardo valor en la heap");			
			
		//estoy de un lado derecho, asi que tengo q cargar lo q esta en ese lugar	
		}else{
			exp.generar();
			Principal.gen.gen("ADD", "Muevo el puntero del arreglo");
			Principal.gen.gen("LOADREF 0", "Cargo el valor del heap");	
		}
		
		//genero el encadenado
		enc.generar();
		
	}
	
}
