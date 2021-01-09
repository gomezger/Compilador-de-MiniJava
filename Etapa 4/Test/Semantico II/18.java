//error: se quiere asignar un tipo clase a un tipo clase y no son compatibles

class Barco extends Transporte{
	private Barco x;
	
	
	Barco (int a, boolean esta){
		x = new Transporte();;
	}

	
}


class Transporte{
	public int[] arreglo;
	public int r;
	public Barco b;
	
	
}


class Main{
	
	static void main(){}
	
}