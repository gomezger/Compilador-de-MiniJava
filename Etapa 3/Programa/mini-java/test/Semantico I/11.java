//verifica que un ctor tenga nombre dist
class Auto extends Vehiculo{
	public int puertas;
	
	Auto2(int a, Vehiculo v){
		
	}
	Auto(Vehiculo v){
		
	}
	
}

class Vehiculo{
	private int ruedas;
	
	
	static void main(){
		
	}
	
	
	static void setRuedas(int a){
		this.ruedas = a;
	}
	dynamic void setRuedas(Auto a, boolean b){
		if(b)
			this.ruedas = a;
	}
	
	
}

class Moto extends Vehiculo{
	public int cilindradas;
	
	
	static void setRuedas(Auto a, boolean b){
		this.ruedas = b-1;
	}
	

}