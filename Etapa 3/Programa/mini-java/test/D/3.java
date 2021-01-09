//Aca se testeara el chequeo de variables.

class A {
	public int a;
	public char b;
	public String c;
	public B miB;
}

class B extends System{
	
}


//Hasta aca todo bien.

//Error: Variable de clase no declarada.

class C {
	private MiClase clase;
}

//Error variable en cadena de ancestros.
class D {
	public int a;
	public char b;
}

class E extends D {
	public int c;
}

class F extends E {
	public String d;
}

class G extends F {
	//private A a;
}
