class A {


	dynamic B m1(){
		return new B();
	}
}


class B {
	dynamic C m2(){
		return new C();
	}
}



class C {
	dynamic D m3(){
		return new D();
	}
}



class D {
	dynamic int m4(){
		return 25;
	}
}

class Principal{
	static void main(){
		(System.printIln(new A().m1().m2().m3().m4()));
	}
}
