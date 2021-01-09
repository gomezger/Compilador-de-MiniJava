//error: verificar si se le asigna a un boolean un valor entero de un arreglo

class Barco extends Transporte{
	
	
}


class Transporte{
	public int[] arreglo;


	dynamic void m1(){
		arreglo = new int[5];
		arreglo[2] = 9;
		boolean a = arreglo[2];
	}
	
	
}


class Main{
	
	static void main(){}
	
}