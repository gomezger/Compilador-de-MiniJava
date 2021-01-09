class A{
	static void main(){
		(new B());
	}
}

class B{
	public int x;
	
	B(){
		int x;
		x = 10;
		(System.printI(x));
	}
}