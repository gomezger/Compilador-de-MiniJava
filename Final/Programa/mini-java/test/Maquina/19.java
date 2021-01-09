class A {
	static int m1(){
		return 1;
	}
}

class B {
	static int m1(){
		return 2;
	}
}

class C {
	static int m1(){
		return 3;
	}
}

class Principal {
	static void main(){
		(System.printIln(A.m1()));
		(System.printIln(B.m1()));
		(System.printIln(C.m1()));


	}
}