package etapa3Exp;




public class ExceptionSinMain extends ExceptionSemanticoDeclaracion {

	private static final long serialVersionUID = 1L;

	public ExceptionSinMain(){
		super("No se defini� al menos un m�todo 'static void main()' en alguna de las clases.");
	}
	
	public ExceptionSinMain(String mensaje){
		super(mensaje);
	}
}
