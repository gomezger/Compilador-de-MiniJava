//error: verificar si se le asigna a un int un valor boolean de un arreglo

class Barco extends Transporte{
	
	
}


class Transporte{
	public int[] arreglo;


	dynamic void m1(){
		boolean[] arreglo = new boolean[5];
		arreglo[2] = true;
		int a = arreglo[2];
	}
	
	
}


class Main{
	
	static void main(){}
	
}