//Verifico que anden los arreglos
class A{
	public char[] cadena;

	A(){
		cadena = new char[13];
		cadena[0] = 'r';
		cadena[1] = 'o';
		cadena[2] = 'c';
		cadena[3] = 'k';
		cadena[4] = '_';
		cadena[5] = 'w';
		cadena[6] = 'i';
		cadena[7] = 't';
		cadena[8] = 'h';
		cadena[9] = '_';
		cadena[10] = 'y';
		cadena[11] = 'o';
		cadena[12] = 'u';
	}

}

class Main {
	static void main(){
		A miClase;
		miClase = new A();

		int i;
		
		i = 0;
		while (i < 13){

			(System.printC(miClase.cadena[i]));
			i = i +1;
		}
	}


}