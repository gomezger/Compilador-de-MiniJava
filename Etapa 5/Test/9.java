//Verifico el manejo de variables locales.

class A {

	static void main(){
		int a,b,c;
		a = 1;
		b = 2;
		c = 3;
		(System.printSln("Inicio Bloque Principal"));
		(System.printIln(a));
		(System.printIln(b));
		(System.printIln(c));


		{
		(System.printSln("Inicio Bloque 1"));

			int d,e,f;
			d = a;
			e = b;
			f = c;
		(System.printIln(a));
		(System.printIln(b));
		(System.printIln(c));
		(System.printIln(d));
		(System.printIln(e));
		(System.printIln(f));
			{
				(System.printSln("Inicio Bloque 2"));

				int g,h,i;
				g = d;
				h = e;
				i = f;
				(System.printIln(a));
				(System.printIln(b));
				(System.printIln(c));
				(System.printIln(d));
				(System.printIln(e));
				(System.printIln(f));
				(System.printIln(g));
				(System.printIln(h));
				(System.printIln(i));
				(System.printSln("Fin Bloque 2"));

			}
			int g,h,i;
				g = 123;
				h = 456;
				i = 789; 
				(System.printIln(a));
				(System.printIln(b));
				(System.printIln(c));
				(System.printIln(d));
				(System.printIln(e));
				(System.printIln(f));
				(System.printIln(g));
				(System.printIln(h));
				(System.printIln(i));

				(System.printSln("Fin Bloque 1"));

		}
		int d,e,f;

			d = 111;
			e =	222;
			f = 333;
		(System.printIln(a));
		(System.printIln(b));
		(System.printIln(c));
		(System.printIln(d));
		(System.printIln(e));
		(System.printIln(f));

				(System.printSln("Fin Bloque Principal"));

	}

}