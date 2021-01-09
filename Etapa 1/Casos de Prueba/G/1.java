//en este archivo vamos a probar que funciona el armado de Tokens cuando no hay errores lexicos.

class Barco extends Mar{
	//atributos
	private String cadena;
	private boolean pertenece;
	public char caracter;
	public int numero;
	
	//constructor
	public Barco(String cadena){
		this.cadena = cadena;
		pertenece = false;
		int n;
		this.numero,n = 10;
		int[] array = new  int[8];
		array = [1,2,3,4,5,6,7,8];
		caracter = 'a';
		caracter = '\a';
		caracter = '\n';
		caracter = '\t';
	}
	
	/* getters */
	public dynamic String getCadena(){
		return this.cadena;
	}
	
	public dynamic void setPertenece(boolean estado){
		this.pertenece = estado;
	}
	
	public dynamic void setPerteneceTrue(){
		this.pertenece = true;
	}
	
	public dynamic int getSuma(){
		return ((5*10)/10)+5-0;
	}

	public static Barco crear(){
		return new Barco("Hola \t gente \n\ aca sigue el texto");
	}
	
	public dynamic int sumar(){
		int suma = 0;
		while(i<10 && i!=10 && !pertenece){
			suma+=1;
			suma-=10;
			if(suma<=10 && suma <=12){
				if(suma>10 || suma==5){
					if(suma>=10){
						suma = 12;
					}
				}
				Barco b = null;
			}else{
				Barco b = crear();
			}
		}
		return suma;
	}	
}