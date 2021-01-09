class A {
	static void main(){
		(System.printIln((m1()).a));
		(System.printIln( (m1()).m1()));
	}

	static B m1(){
		return new B();
	}
}

class B{
	public int a;

	B(){
		a = 1;
	}
	dynamic int m1(){
		return 10;
	}
}