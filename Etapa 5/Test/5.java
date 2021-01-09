class A {
	public int attr1;

	dynamic void m1(int param1){
		int varL1;
		varL1 = 20;
		(System.printIln(attr1));
		(System.printIln(varL1));
		(System.printIln(param1));
		attr1 = 50;
		varL1 = 60;
		param1 = 70;
		(System.printIln(attr1));
		(System.printIln(varL1));
		(System.printIln(param1));
	}

	A(){
		attr1 = 10;
	}
}

class Main{
	static void main(){
		A miClase;
		miClase = new A();
		
		
		(miClase.m1(30));
	}

}