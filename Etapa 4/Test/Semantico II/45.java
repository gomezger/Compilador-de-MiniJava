//error: llamada metodo encadenado

class Barco extends Transporte{
	private Barco x;
	public int a;
	public Main m;
	
	
	Barco (int a, boolean esta){
		boolean er;
	}
	
	
	dynamic void m2(){
		
		m.m1(null) = 6;
		
	}
	
}


class Transporte{
	public int[] arreglo;
	public int r;
	public Barco b;
	
	
	dynamic int m1(Barco a){
		
		b = new Barco(3,true);
		
		return b.a;
	}
	
}


class Main{
	
	static void main(){}
	
}