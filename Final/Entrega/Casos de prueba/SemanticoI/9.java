//verifica henerecia circular
class Auto extends Vehiculo{
	public int puertas;
	
	Auto(int a, Vehiculo v){
		
	}
	Auto(Vehiculo v){
		
	}
	
}

class Vehiculo extends Auto{
	private int ruedas;
	
	
	static void main(){
		
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
	
	
	static void setRuedas(int a){
		this.ruedas = b-1;
	}
	

}