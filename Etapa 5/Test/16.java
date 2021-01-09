class A{
	public int a;
	
	
	A(int b){
		int c ;
		c = m1(10);
		(System.printIln(c));		
	}
	
	static void main(){		
		A objeto ;
		objeto = new A(1);
		int d ;
		d = objeto.m1(5);
		(System.printIln(d));
		d = objeto.m2(5);
		(System.printIln(d));
	}
	
	dynamic int m1(int a){
		(System.printIln(a+1));
		return 5+a;	
	}

	static int m2(int a){
		(System.printIln(a+2));
		return 6+a;
	}
}