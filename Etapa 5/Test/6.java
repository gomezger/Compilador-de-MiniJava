class A {
	public String a;
	A(){
		this.a = "Soy de A";
	}
}

class B {
	public A clase;
	public String b;
	B(){
		this.b = "Soy de B";
		clase = new A();
	}
}

class C{
	public B clase;
	public String c;
	C(){
		this.c = "Soy de C";
		clase = new B();
	}
}

class Principal {
	static void main(){
		C miClase;
		miClase = new C();
		(System.printSln(miClase.c));
		(System.printSln(miClase.clase.b));
		(System.printSln(miClase.clase.clase.a));


	}
}