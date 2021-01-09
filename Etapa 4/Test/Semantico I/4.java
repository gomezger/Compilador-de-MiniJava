//verifica que hay parametros en el main, ahora se puede, por lo cual solo dice que no hay main
class Auto extends Vehiculo{
	public int puertas;
	
	Auto(int a, Vehiculo v){
		
	}
	Auto(Vehiculo v){
		
	}
	
}

class Vehiculo extends Object{
	private int ruedas;
	
	
	static void main(int a){
		
	}
	
	
	static void setRuedas(int a){
		this.ruedas = a;
	}
	dynamic void setRuedas(int a, boolean b){
		if(b)
			this.ruedas = a;
	}
	
	
}

class Moto extends Vehiculo{
	public int cilindradas;
	
	
	static void setRuedas(int b){
		this.ruedas = b-1;
	}
	

}