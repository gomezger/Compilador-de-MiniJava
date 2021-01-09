package etapa4AST;
import etapa1.Principal;
import etapa1.Token;
import etapa3Entradas.EntradaClase;
import etapa3Entradas.EntradaConParams;
import etapa3Exp.ExceptionSemantico;
import etapa3Tipos.TipoBase;
import etapa3Tipos.TipoIdClase;
import etapa4Exp.ExceptionNoEsIdClase;
import etapa4Exp.ExceptionUnidadNoExiste;

public class NLlamadaCtorClase extends NLlamadaCtor {
	//atributos
	protected NArgsActuales args;
	protected TipoIdClase tipo;
	public EntradaClase miClase;
	public EntradaConParams ctor;
	
	//constrcutor
	public NLlamadaCtorClase(Token token,TipoIdClase tipo, NArgsActuales args, NEncadenado enc){
		super(token,enc);
		this.args = args;
		this.enc = enc;
		this.tipo = tipo;
	}
	
	//getters
	public NArgsActuales getArgs() {
		return args;
	}
	public TipoIdClase getTipo() {
		return tipo;
	}
	

	//chequear
	public TipoBase chequear() throws ExceptionSemantico{
		
		// ... = new nombreClase(...)
		String nombreClase = token.getLexema();
		
		//me devuelve la clase con ese nombre, si no lanza excepsion
		if(Principal.ts.existeClase(nombreClase)){
		
			miClase = Principal.ts.getClase(nombreClase);
				
			//pido el contructor con la cantidad de argumentos
			ctor = miClase.getConstructores().get(args.getLista().size());
			
			//verifico si es distinto a nulo
			if(ctor!=null){
				
				//chequeamos los parametros
				args.chequear(ctor.getListaParametros());
				
				//chequeamos el encadenado
				return enc.chequear(tipo);
				
			}else{
				throw new ExceptionUnidadNoExiste(args.getLista().size(),nombreClase, token.getLinea(), token.getColumna());
			}
		}else{
			throw new ExceptionNoEsIdClase(nombreClase,token.getLinea(),token.getColumna());
		}
		
	}
	
	
	public void imprimir(int n){
		tabs(n); 
		System.out.println("Llamada Ctor Clase");
		System.out.println("   Tipo: "+tipo.nombre+"");
		args.imprimir(n+1);
		enc.imprimir(n+1);
	}

	
	public void generar() {
		
		Principal.gen.gen("RMEM 1", "");
		Principal.gen.gen("PUSH "+(miClase.getAtributos().size()+1), "Espacio que va a ocupar el objeto");
		Principal.gen.gen("PUSH simple_malloc", "Reservo espacio para el objeto");
		Principal.gen.gen("CALL", "llamo a simple malloc");
		Principal.gen.gen("DUP", "Duplico el this del objeto creado");
		Principal.gen.gen("PUSH VT_"+miClase.getNombre(), "apilo la VT de la clase");
		Principal.gen.gen("STOREREF 0", "Guardo la VT");
		Principal.gen.gen("DUP", "");
		
		//genero parametros
		this.args.generarConSwap();
		
		//voy al constructor
		Principal.gen.gen("PUSH "+ctor.getEtiqueta(), "Quiero llamar al ctor");
		Principal.gen.gen("CALL", "Llamo al ctor");		
		
		//genero el codigo del encadenado
		enc.generar();
	}

}
