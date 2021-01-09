//Aca verifico si correctamente genera el codigo heredado

class A {
	dynamic void m1(){
		(System.printSln("Soy m1 de la clase A"));
	}
	dynamic void m2(){
		(System.printSln("Soy m2 de la clase A"));

	}
	dynamic void m3(){
		(System.printSln("Soy m3 de la clase A"));

	}
}

class B extends A{
	dynamic void m1(){
		(System.printSln("Soy m1 de la clase B"));
	}

	dynamic void m3(){
		(System.printSln("Soy m3 de la clase B"));

	}
}

class C extends B{
	
}

class D extends C{
	dynamic void m1(){
		(System.printSln("Soy m1 de la clase D"));	
	}

	dynamic void m2(){
		(System.printSln("Soy m2 de la clase D"));

	}

	dynamic void m3(){
		(System.printSln("Soy m3 de la clase D"));

	}
}

class Main {
	static void main(){
		A miClase;
		miClase = new A();

		(miClase.m1());
		(miClase.m2());

		miClase = new B();

		(miClase.m1());
		(miClase.m2());
		(miClase.m3());

		miClase = new C();

		(miClase.m1());
		(miClase.m2());
		(miClase.m3());


		miClase = new D();

		(miClase.m1());
		(miClase.m2());
		(miClase.m3());


	}
}



