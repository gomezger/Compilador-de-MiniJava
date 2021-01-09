class A {


	dynamic int m1(){
			if (false){
				return 1;
			}else{
				while (true){
					if (5>1){
						if (true){
							return 1+2+3+4+5+6+7+8+9;
						}
						return 20;
					}
					return 30;
				}	

			}

	}
}

class Principal {
	static void main(){
		A clase;
		clase = new A();
		(System.printIln(clase.m1()));
	}
}