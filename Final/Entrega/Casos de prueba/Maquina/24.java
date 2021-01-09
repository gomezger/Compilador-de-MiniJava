class A {
	public int attr1;
	public A clase;

	dynamic void m1(){
		int varL1;
		(System.printIln(this.attr1));
		this.attr1 = 50;
		int valor;
		valor = this.attr1;
		(System.printIln(valor));

		clase = new A();
		(System.printIln(this.clase.attr1));

	}

	A(){
		this.attr1 = 10;
	}
}

class Main{
	static void main(){
		A miClase;
		miClase = new A();
		(miClase.m1());
	}

}