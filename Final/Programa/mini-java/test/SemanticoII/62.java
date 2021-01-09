//error: return en constrcutor

class A{
	private int a1;
}

class B extends A{
	B(){
		a1 = 10;
	}
	
	static void main(){}
}