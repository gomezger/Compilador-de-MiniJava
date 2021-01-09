//error: al llamar a un metodo que retorna true y su metodo pedia boolena
class Barco{
	
	static void main(){}
	
	dynamic Auto m1(int a){
		return new Auto();		
	}
	
	static boolean m2(){
		m1(6).x = 6;
		return true;
	}
	
}


class Auto{
	public int x;
	
	
}
