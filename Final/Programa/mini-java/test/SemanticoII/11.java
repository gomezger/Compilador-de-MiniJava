//error: se quiere acceder a una variable de una variable q no es clase

class Barco extends Transporte{
	
	
}


class Transporte{
	public int[] arreglo;
	public int a;

	dynamic void m1(){
		int x = a.x;
	}
	
	static int m2(){
		return 5;
	}
	
	
}


class Main{
	
	static void main(){}
	
}