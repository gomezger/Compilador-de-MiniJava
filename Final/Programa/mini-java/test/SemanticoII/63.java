//error: return en constrcutor

class A{
	private int a1;
}

class B extends A{
	
	B(){
		int b;
		int a = (b)[23];
	}
	
	static void main(){}
}