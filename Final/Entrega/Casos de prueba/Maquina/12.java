class A {
	public int[] arreglo;

	A(int a){
		arreglo = new int[a];

		(System.printIln(new int[1][0]));
	}

	dynamic void setAtIndex(int a,int c){
		arreglo[a] = c;
	}

	dynamic void printAt(int a){
		(System.printIln(arreglo[a]));
	}
}


class Principal {
	static void main(){
		int a;
		a = 15;

		A clase;
		clase = new A(a);


		int contador ;
		contador = 0;
		int c;
		while (contador < a){
			c = System.read();
			(clase.setAtIndex(contador,c));
			contador = contador +1;
		}

		contador = 0;
		while (contador < a){
			(clase.printAt(contador));
			contador = contador+1;
		}





	}


}