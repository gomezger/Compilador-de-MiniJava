class Principal {
	static void main(){
		int a;
		int contador;
		contador = 0;
		a = 20;

		while (contador > a){
			(System.printSln("Aca no deberia entrar nunca"));
		}


		while (contador < a){
			(System.printS("Estoy en la iteracion "));
			(System.printIln(contador));

			if (contador == a-1){
				(System.printSln("Esta fue la ultima iteracion"));
			}
			contador = contador +1;
		}


		
		(System.printSln("Ahora probamos con doble while"));
		int i,j;
		i = 0;
		j= 0;
		while (i < a){
			while (j < a){
				(System.printS("Ubicacion actual: ")); 
				(System.printI(i));
				(System.printS("  "));
				(System.printIln(j));
				j = j + 1;
			}	
			i = i + 1; 
			j = 0;
		}
	}
}