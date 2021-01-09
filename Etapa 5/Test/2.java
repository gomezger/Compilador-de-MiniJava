//verifico que reservo espacio para variables y lo elimino bien

class A{
	public int a;
	public int b;
	
	static void main(){
		(System.printIln(4));
		(System.printBln(false));
		(System.printCln('a'));
		//(System.printIln(System.read()));
	}
	
	dynamic int m1(){
		int a,b;		
		
		{
			int e,f,y;
			
		}
		
	}
	dynamic int m2(){}
}


class B extends A{
	public int c;
	public int d;
	
	dynamic int m3(int a, int b){}
	dynamic int m4(){}
}

class C extends B{
	public int e;
	public int f;
	
	dynamic int m3(int a){}
	dynamic int m5(){}
}

class D extends C{
	dynamic int m1(int b){}
	dynamic int m1(){}
}

