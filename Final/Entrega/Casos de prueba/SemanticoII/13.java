//error: se quiere acceder a una variable de una variable de tipo clase, pero es un atributo privado

class Barco extends Transporte{
	private int x;
	
}


class Transporte{
	public int[] arreglo;
	public int r;
	public Barco b;

	dynamic void m1(){
		int x = b.x;
	}
	
	static int m2(){
		return 5;
	}
	
	
}


class Main{
	
	static void main(){}
	
}