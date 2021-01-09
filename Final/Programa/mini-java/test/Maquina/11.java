//Aca verifico que el constructor anda correctamente.

class A{
	public int attr1;
	public int attr2;
	public String cadena;

	A(int a,int b,String c){
		attr1 = a;
		attr2 = b;
		cadena = c;
	}

	

}

class Main{
	static void main(){
		A miClase;
		miClase = new A(20,40,"Hola Gotti");

		(System.printIln(miClase.attr1));
		(System.printIln(miClase.attr2));
		(System.printSln(miClase.cadena));
		(System.printIln(new A(10,20,"Hello").attr1));


	}
}