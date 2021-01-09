//Aca verifico si genera el codigo de todas las clases

class A {
	dynamic void m1(){
		(System.printSln("Soy de la clase A"));
	}
}

class B {
	dynamic void m1(){
		(System.printSln("Soy de la clase B"));
	}
}

class C {
	dynamic void m1(){
		(System.printSln("Soy de la clase C"));
	}
}

class D {
	dynamic void m1(){
		(System.printSln("Soy de la clase D"));
	}
}

class Main {
	static void main(){
		A a ;
		a = new A();
		B b ;
		b = new B();
		C c; 
		c = new C();
		D d ;
		d = new D();

		(a.m1());
		(b.m1());
		(c.m1());
		(d.m1());

	}
}



