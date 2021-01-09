package etapa3Exp;




public class ExceptionSinMain extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionSinMain(){
		super("No se definió al menos un método 'static void main()' en alguna de las clases.");
	}
	
	public ExceptionSinMain(String mensaje){
		super(mensaje);
	}
}
