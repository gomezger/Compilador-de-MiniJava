 class A {}

 class B {}

 class C extends A {}

 class D extends B {}

//Hasta aca deberia ir todo bien

//Error 1: La clase no existe
// class E extends ClaseNoExistente {}

//Error 2: Herencia circular
 //class F extends G{
	
//}

// class G extends F{
//
//}

class E extends H {}

class F extends E{}

class G extends F{}

class H extends G{}