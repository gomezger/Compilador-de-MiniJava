class A {
	static void m1(char[] a){}
	static void m1(int a, int b, int c){}
}

class B extends A {
	static void m1(){}
}

//Error 1: Heredo un metodo con mismo nombre pero con dynamic
//class C extends A{
//	dynamic void m1(int a){}
//}

//Error 2: Heredo un metodo con mismo nombre, misma forma metodo, pero distinto tipo
//class C extends A{
//	dynamic int m1(int a){}
//}

//Error 3: Heredo un metodo con mismo nombre, mismo tipo, misma forma metodo, pero distinto tipo de Parametro
class C extends A{
	static void m1(int[] a){}
}









