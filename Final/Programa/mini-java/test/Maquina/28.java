class A{
	public int x;
	
	dynamic void setVA(){
		x = 1;
	}
	
	dynamic int m1(){
		return m2()+1;
	}
	
	dynamic int m2(){
		return x;
	}
}

class B extends A{
	public int y;
	
	B(){
		(setVB());
	}
	
	dynamic void setVB(){
		y = 2;
		(setVA());
	}
	
	dynamic int m2(){
		return y;
	}
	
	static void main(){
		A x;
		x = new B();
		(System.printIln(x.m1()+x.m2())); //5
	}
}