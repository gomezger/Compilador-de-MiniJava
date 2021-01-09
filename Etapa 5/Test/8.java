class A {
	public int attr;
	A(){
		int a;
		a = this.m1();
		String s;
		s= this.m2();
		this.attr = m1()*2+6+9/2;

		(System.printIln(a));
		(System.printSln(s));
		(System.printIln(this.attr));
	}

	dynamic int m1(){
		return 20;
	}

	dynamic String m2(){
		return "Hola";
	}


	static void main(){
		A clase;
		clase = new A();
	}
}