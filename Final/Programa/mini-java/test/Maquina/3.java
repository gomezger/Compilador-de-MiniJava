//probar si funcionan los if

class A{
	public int a;
	public int b;
	
	static void main(){
		
		if(false)
			(System.printIln(0));
		
		if(true)
			(System.printIln(1));
		else
			(System.printIln(2));
		
		if(true)
			(System.printIln(3));
			
		
	}
	
	dynamic int m1(){
		
	
		
		
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

