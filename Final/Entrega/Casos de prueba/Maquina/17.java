//Verifico que se llame al metodo correcto, y que este funcione bien

class A{
	public int attr1;
	public int attr2;
	public String cadena;

	A(int a,int b,String c){
		attr1 = a;
		attr2 = b;
		cadena = c;
	}

	static void m2(int a,int b){
		(System.printSln("No hago nada en este metodo"));
	}


	dynamic String m1(int a,int b){
		if (a<b)
			return "Hola don Pepito";
		else
			return "Hola don Jose";
	}

	dynamic void m0(int a,int b){

	}

}

class Main{
	static void main(){
		A miClase;
		miClase = new A(20,40,"Hola Gotti");

		String s ;
		s = miClase.m1(1,5);
		(System.printSln(s));
		s = miClase.m1(50,5);
		(System.printSln(s));
		(miClase.m2(25,14));




	}
}