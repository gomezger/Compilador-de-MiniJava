//Se testeara el chequeo de metodos.


class A {
	static void m1(){}

	dynamic int m2(int a, A miclase, String b){}


	//Hasta aca todo bien.

	//Error retorno desconocido:
	static MiClase m3(){}

	//Error parametros desconocidos.
	dynamic int m4(int c,char b,MiClase a){}
}
