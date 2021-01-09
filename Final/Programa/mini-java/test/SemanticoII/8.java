//error: estoy en un metodo estatico y no puedo hacer this.algo

class Barco extends Transporte{
	
	
}


class Transporte{
	public int[] arreglo;
	public int x;

	static void m1(){
		this.m2() = 5;;
	}
	
	static int m2(){
		return 5;
	}
	
	
}


class Main{
	
	static void main(){}
	
}