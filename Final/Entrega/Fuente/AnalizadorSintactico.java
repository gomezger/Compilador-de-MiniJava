


import java.util.LinkedList;


public class AnalizadorSintactico {
	private AnalizadorLexico alex;
	private Token token;
	private boolean estoyEnAtr = false;

	/**
	 * Construtor de la clase, recibe un analizador lexico y setea el primer token
	 * @param alex
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	public AnalizadorSintactico(AnalizadorLexico alex) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.alex = alex;
		this.setNextToken();
	} 
	
	/**
	 * <Empezar> ::= <Inicial> null
	 * Es el encargado de empezar el analizador sintactico
	 * @throws ExceptionLexico Error Lexico
	 * @throws ExceptionSintactico Error Sintactico
	 * @throws ExceptionSemanticoDeclaracion 
	 */
	public void empezar() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		/**SEMANTICO*/
		Principal.ts = new TablaDeSimbolos();
		/***/
		
		this.inicial();
		
		//verifico un token null que es igual a fin de archivo
		if(comp("eof")){
			//termino
		}
	}
	
	
	/**
	 * <Inicial> ::= <Clase><MasClase>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 * @throws ExceptionSemanticoDeclaracion 
	 */
	private void inicial() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion, ExceptionSemanticoDeclaracion{
		this.clase();
		this.masClase();		
	}
	
	/**
	 * <Clase> ::= class IdClase<Herencia>{ <Miembro>}
	 * @throws ExceptionLexico Error Lexico
	 * @throws ExceptionSintactico Error Sintactico
	 * @throws ExceptionSemanticoDeclaracion 
	 */
	private void clase() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//me fijo si hay un class
		match("class");
		
		
		try{	
			
			/** SEMANTICO */
			Principal.ts.addClase(new EntradaClase(token));
			/** */	
			
		}catch(ExceptionClase e){
			
			System.out.println(e.getMessage());
			
			Principal.ts.setHayErrores();
			
			//salteo las clases hasta el proximo class o eof
			this.saltearClaseExiste();
			
			//termino
			return;
		}
		
			
		//me fijo si esta el IdClase
		match("IdClase");
		
		//voy a herencia
		this.herencia();
				
		//consumo una {
		match("{");
					
		//voy a miembro
		this.miembro();
					
		//consumo una }
		match("}");
		
		/** SEMANTICO */
		//agrego un constrcutor si no hay
		if(Principal.ts.getClaseActual().getCantConstructores()==0){
			Principal.ts.addConstructor(new EntradaConstructor(new Token("IdClase",Principal.ts.getClaseActual().getLexema(),0,0),Principal.ts.getClaseActual()));
		}
		/***/
		
	}
	
	
	/**
	 * <Miembro> ::= <Atributo><Miembro> | <Ctor><Miembro> | <Metodo><Miembro> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void miembro() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		// me fijo si es <atributo> viendo sus primeros
		if(comp("public") || comp("private")){
			this.estoyEnAtr = true;
			this.atributo();
			this.miembro();
		
		// me fijo si es <Ctor> viendo sus primeros
		}else if(comp("IdClase")){
			
			
			try{	
				this.estoyEnAtr = false;

				EntradaConParams ctor = this.ctor();
				
				/** SEMANTICO */
				Principal.ts.addConstructor(ctor);
				/***/
				
			}catch(ExceptionConstructor | ExceptionConstructorMalo e){
				
				System.out.println(e.getMessage());			
				Principal.ts.setHayErrores();	
				//sigo porque como capture cosntrcutores termiandos, no hay que saltear
				
			}catch(ExceptionParametro e){
			
				System.out.println(e.getMessage());
				Principal.ts.setHayErrores();			
				this.saltearParametros();
				this.saltearBloque();
				
			}
						
			this.miembro();
			
		//me fijo si es <Metodo> viendo sus primeros	
		}else if(comp("static") || comp("dynamic")){

			try{

				this.estoyEnAtr = false;
				this.metodo();
			
			}catch(ExceptionParametro e){
				
				System.out.println(e.getMessage());
				Principal.ts.setHayErrores();			
				this.saltearParametros();
				this.saltearBloque();
				
			}
			
			
			this.miembro();
						
		}else if(comp("}")){
			
			//termino miembro
			
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba public, private, identificador de clase, static, dynamic o llave de cierre.", token.getColumna(), token.getLinea());
		}
		
	}
	
	/**
	 * <Metodo> ::= <FormaMetodo><TipoMetodo>idMetVar<ArgsFormales><Bloque>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void metodo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		boolean dinamico = this.formaMetodo();
		TipoBase tipo = this.tipoMetodo();
		
	
		/**SEMANTICO*/
		EntradaConParams met = new EntradaMetodo(dinamico, tipo, token,Principal.ts.getClaseActual());
		/***/

			
		match("IdMetVar");
		
		met = this.argsFormales(met);
		

		/**SEMANTICO */
		try{
			Principal.ts.addMetodo((EntradaMetodo)met);
			
		}catch(ExceptionMetodo | ExceptionTipoMainEstatico | ExceptionMainEstatico e){
			
			System.out.println(e.getMessage());
			Principal.ts.setHayErrores();			
			this.saltearBloque();
			
			return;
		}	
		/***/
		
		
		//guardo el bloque en 
		NBloque bloque = this.bloque(null);		
		bloque.setUnidad(met);
		met.setBloque(bloque);
		
	}
	
	/**
	 * <FormaMetodo> ::= static | dynamic
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private boolean formaMetodo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//es static
		if(comp("static")){
			match("static");
		
			return false;
		//es dynamic
		}else if(comp("dynamic")){
			match("dynamic");
		
			return true;
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba 'static' o 'dynamic'", token.getColumna(), token.getLinea());
		}
	}
	
	/**
	 * <TipoMetodo> ::= <Tipo> | void
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private TipoBase tipoMetodo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//es <tipo>
		if(comp("boolean") || comp("char") || comp("int") || comp("IdClase") || comp("String")){
			return this.tipo();
		
		//es void	
		}else if(comp("void")){
			match("void");
			return new TipoVoid();
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un tipo o 'void'", token.getColumna(), token.getLinea());
		}
	}

	/**
	 * <Ctor> ::=idClase<ArgsFormales><Bloque>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private EntradaConParams ctor() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		/** SEMANTICO */
		EntradaConParams ctor = new EntradaConstructor(token,Principal.ts.getClaseActual());	
		/***/
		
		match("IdClase");
		
		ctor = this.argsFormales(ctor);
		
			
		//agrego el bloque al ctor
		NBloque bloque = this.bloque(null);
		bloque.setUnidad(ctor);
		ctor.setBloque(bloque);
		
		return ctor;
	}
	
	
	/**
	 * <ArgsFormales> ::= ( <ListaArgsFormalesAux>)
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private EntradaConParams argsFormales(EntradaConParams ctor) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		match("(");
		EntradaConParams aux = this.listaArgsFormalesAux(ctor);
		match(")");
		return aux;
	}
	
	/**
	 * <ListaArgsFormalesAux> ::= <ListaArgsFormales> | vacio
	 * @throws ExceptionSintactico
	 * @throws ExceptionLexico
	 */
	private EntradaConParams listaArgsFormalesAux(EntradaConParams ctor) throws ExceptionSintactico, ExceptionLexico, ExceptionSemanticoDeclaracion{
		//es un tipo
		if(comp("boolean") || comp("char") || comp("int") || comp("IdClase") || comp("String")){
			return this.listaArgsFormales(ctor);
		
		//es vacio
		}else if(comp(")")){
			//termino
			return ctor;
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un tipo o un ')'", token.getColumna(), token.getLinea());
		}
	}
	
	
	/**
	 * <ListaArgsFormales> ::= <ArgFormal><F1>
	 * @throws ExceptionSintactico
	 * @throws ExceptionLexico 
	 * @throws ExceptionSemanticoDeclaracion 
	 */
	private EntradaConParams listaArgsFormales(EntradaConParams ctor) throws ExceptionSintactico, ExceptionLexico, ExceptionSemanticoDeclaracion, ExceptionSemanticoDeclaracion{
		EntradaParametro param = this.argFormal();
		
		/** SEMANTICO */
		ctor.addParam(param);
		/***/
		
		return this.f1(ctor);
	}
	
	/**
	 * <ArgFormal> ::= <Tipo>idMetVar
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private EntradaParametro argFormal() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		Tipo tipo = this.tipo();
		
		/**SEMANTICO*/
		EntradaParametro param = new EntradaParametro(tipo,token);	
		/***/
		
		match("IdMetVar");
		
		return param;
	}
	
	
	/**
	 * <F1> ::= ,<ListaArgsFormales> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private EntradaConParams f1(EntradaConParams ctor) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//es una coma
		if(comp(",")){
			match(",");
			return this.listaArgsFormales(ctor);
		
		// es vacio
		}else if(comp(")")){
			//termine
		
			return ctor;
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba una ',' o un ')'", token.getColumna(), token.getLinea());
		}
		
	}
	
	
	/**
	 * <Bloque> ::= {<MasSentencia>}
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NBloque bloque(NBloque padre) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		Token t = this.token;
		
		//creo el bloque hijo de padre
		NBloque hijo = new NBloque(t,padre,Principal.ts.getMetodoActual());
		
		//seteo el nuevo bloque actual
		Principal.ts.bloqueActual = hijo;
		
		match("{");
		LinkedList<NSentencia> sentencias = this.masSentencia(new LinkedList<NSentencia>(), hijo);
		match("}");
		
		hijo.setSentencias(sentencias);
		Principal.ts.bloqueActual = padre;
		return hijo;
	}
	
	
	/**
	 * <MasSentencia> ::=  <Sentencia><MasSentencia> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<NSentencia> masSentencia(LinkedList<NSentencia> sentencias, NBloque padre) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		//es sentencia
		if(comp(";") || comp("this") || comp("IdMetVar") || comp("(") ||  comp("boolean") || 
			comp("char") || comp("int") || comp("IdClase") || comp("String") || 
			comp("if") || comp("while") || comp("{") || comp("return")){
			
			//pido las sentencias de la linea
			LinkedList<NSentencia> aux =  this.sentencia(padre);
			sentencias.addAll(aux);
			
			//veo si hay mas sentencias
			return this.masSentencia(sentencias, padre);
		
		//termino mas sentencia	
		}else if(comp("}")){
			//termino
			
			return sentencias;
			
		}else{	
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba una '}' o una sentencia válida", token.getColumna(), token.getLinea());
		}
	}
	
	private NSentencia sentenciaSola(NBloque padre) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		return this.sentencia(padre).getFirst();
	}
	
	/**
	 * <Sentencia> ::= ; | <Asignacion>; | <SentenciaLlamada>; | <Tipo><ListaDecVars>;  |
	 * 		if( <Expresion> ) <Sentencia> <F3> | while ( <Expresion> ) <Sentencia> | <Bloque> | 
	 * 		return <ExpresionOpcional>;
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<NSentencia> sentencia(NBloque padre) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		LinkedList<NSentencia> ret = new LinkedList<NSentencia>();	
		Token t = token;	
		//es un punto y coma
		if(comp(";")){
			
			match(";");
			ret.add(new NPuntoComa(t));
			return ret;			
		
		//es asignacion	
		}else if(comp("this") || comp("IdMetVar")){
			
			NAsignacion asig = this.asignacion();
			match(";");	
			ret.add(asig);
			return ret;
		
		// es sentencia llamada
		}else if(comp("(")){
			
			NSentenciaLlamada sl = this.sentenciaLlamada();
			match(";");

			ret.add(sl);
			return ret;
			
			
			
			
		// es tipo
		}else if(comp("boolean") || comp("char") || comp("int") || comp("IdClase") || comp("String")){			
			Tipo tipo = this.tipo();
			LinkedList<EntradaAtributo> varLocales = this.listaDecVars(new LinkedList<EntradaAtributo>(),false,null);
			Token igual = token;
			NExpresion exp = this.inline();
			match(";");
			
			//recorro la lista de variables
			for(EntradaVariable var: varLocales){
				//creo nodo declaracion
				NDeclaracionVariable nv = new NDeclaracionVariable(t,tipo,var.getNombre(),padre);
				ret.add(nv);
				
				//si es una expresion inline (o sea no es expresion vacia)
				if(!(exp instanceof NExpresionVacia)){
					boolean li = true;
					NAccesoVar accVar = new NAccesoVar(var.getToken(),new NEncadenadoVacio(t),li,estoyEnAtr);
					NAsignacion asig = new NAsignacion(igual,accVar,exp);
					ret.add(asig);
				}
			}
			
			return ret;
			
		
		//es if
		}else if(comp("if")){
			
			match("if");
			match("(");
			NExpresion cond = this.expresion(false);
			match(")");
			NSentencia then = this.sentenciaSola(padre);			
			NIf nodoIf = new NIfSinElse(t,cond,then);			
			
			ret.add(this.f3(nodoIf,padre));
			return ret;
		
		//es while	
		}else if(comp("while")){
			match("while");
			match("(");
			NExpresion cond = this.expresion(false);
			match(")");
			NSentencia cuerpo = this.sentenciaSola(padre);
		
			ret.add(new NWhile(t,cond,cuerpo));
			return ret;
			
		//es bloque	
		}else if(comp("{")){
			
			ret.add(this.bloque(padre));
			return ret;
			
		//es return
		}else if(comp("return")){
			
			match("return");
			NExpresion exp = this.expresionOpcional(false); // no esta del lado izq de un =
			match(";");
			
			ret.add(new NReturn(t,exp));
			return ret;
		
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba ';', 'this', un identificador de metodo o variable, un '(', un tipo, un if, un while, un bloque, o 'return'", token.getColumna(), token.getLinea());
		}
		
	}
	
	/**
	 * <Asignacion> ::= <AccesoVar>=<Expresion> | <AccesoThis>= <Expresion> 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NAsignacion asignacion() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//ir a acceso var
		if(comp("IdMetVar")){
			NAccesoVar accVar = this.accesoVar(true); //esta del lado ziquierdo del =
			Token igual = token;
			match("=");
			NExpresion exp = this.expresion(false); // esta del lado derecho del =
			
			return new NAsignacion(igual, accVar, exp);
			
		//ir a acceso this	
		}else if(comp("this")){
			NAccesoThis accThis = this.accesoThis(true); //esta del lado ziquierdo del =
			Token igual = token;
			match("=");
			NExpresion exp = this.expresion(false);// esta del lado derecho del =
		
			return new NAsignacion(igual, accThis, exp);
		
		//es un error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un identificar de clase o variable, o un 'this'", token.getColumna(), token.getLinea());
		}
	}
	
	/**
	 * <ExpresionOpcional> ::=  <ExpOr> | vacio
	 * @return 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expresionOpcional(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//entro a <expOr>
		if(comp("+") || comp("-") || comp("!") || comp("null") || comp("true") || comp("false") 
			|| comp("entero") || comp("caracter") || comp("string") || comp("(") || 
			comp("this") || comp("IdClase") || comp("IdMetVar") || comp("new")){
			
			return this.expOr(li);
		
		//es vacio
		}else if(comp(";")){
			//termino
			return new NExpresionVacia(token);
			
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '+', '-', '!', un literal o primario", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <F3> ::= else<Sentencia> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NIf f3(NIf nodoIf, NBloque padre) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
	
		//me fijo si es else
		if(comp("else")){
			match("else");
			NSentencia elsee = this.sentenciaSola(padre);
		
			return new NIfConElse(nodoIf.getToken(),nodoIf.getCond(),nodoIf.getThen(),elsee);
			
		//me fijo si es vacio
		}else if(comp("}") || comp(";") || comp("IdMetVar") || comp("this") || comp("(")
				|| comp("boolean") || comp("char") || comp("int") || comp("IdClase")||
				comp("String") || comp("if") || comp("while") || comp("{") || comp("return")){
			//termine
			return nodoIf;
			
		//error	
		}else{
			
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba 'else' o '}'", token.getColumna(), token.getLinea());
		}
	}

	/**
	 * <Expresion> ::= <ExpOr>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expresion(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		return this.expOr(li);
	}

	
	/**
	 * <ExpOr> ::=<ExpAnd><ExpOr2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expOr(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		NExpresion exp = this.expAnd(li);
		return this.expOr2(exp,li);
	}

	/**
	 * <ExpOr2> ::= || <ExpAnd><ExpOr2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expOr2(NExpresion exp0, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//es un ||
		if(comp("||")){
			Token t = token;
			match("||");
			NExpresion exp1 = this.expAnd(li);
			NExpresion exp2 = new NExpBinaria(t,exp0,exp1);
			return this.expOr2(exp2,li);
		
		//es vacio
		}else if(comp(")") || comp("]") || comp(";") || comp(",")){
			//termino
			return exp0;
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());
		}
		
		
	}

	/**
	 * <ExpAnd> ::=<ExpIg><ExpAnd2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expAnd(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		NExpresion exp = this.expIg(li);
		return this.expAnd2(exp,li);
	}

	/**
	 * <ExpAnd2> ::=&&<ExpIg><ExpAnd2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expAnd2(NExpresion exp0, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		// es un &&
		if(comp("&&")){
			Token t = token;
			match("&&");
			NExpresion exp1 = this.expIg(li);
			NExpresion exp2 = new NExpBinaria(t,exp0,exp1);
			return this.expAnd2(exp2,li);
		
		//termino
		}else if(comp("||") || comp(")") || comp("]") || comp(";") || comp(",")){			
			//termino
			return exp0;
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());
		}
	}

	/**
	 * <ExpIg> ::=<ExpComp><ExpIg2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expIg(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		NExpresion exp = this.expComp(li);
		return this.expIg2(exp,li);
	}

	/**
	 * <ExpIg2> ::=<OpIg><ExpComp><ExpIg2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expIg2(NExpresion exp0, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es OpIg
		if(comp("==") || comp("!=")){
			Token t = token;
			this.opIg();
			NExpresion exp1 = this.expComp(li);
			NExpresion exp2 = new NExpBinaria(t,exp0,exp1);
			return this.expIg2(exp2, li);
		
		}else if(comp("||") || comp(")") || comp("]") || comp(";") || comp(",") || comp("&&")){
			//termino
			return exp0;
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());
		}
		
	}
	

	/**
	 * <ExpComp> ::=<ExpAd ><F8>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expComp(boolean li)  throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		NExpresion exp = this.expAd(li);
		return this.f8(exp,li);
	}

	
	/**
	 * <F8> ::=  <OpComp><ExpAd> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion f8(NExpresion exp0, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		// es OpComp
		if(comp("<") || comp(">") || comp(">=") || comp("<=")){
			Token t = token;
			this.opComp();
			NExpresion exp1 = this.expAd(li);
			return new NExpBinaria(t,exp0,exp1);
		
		//es vacio
		}else if(comp("==") || comp("!=") || comp("||") || comp(")") || comp("]") || 
				comp(";") || comp(",") || comp("&&")){
			//termino
			return exp0;
			
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());	
		}
	}
	/**
	 * <ExpAd> ::=<ExpMul><ExpAd2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expAd(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		NExpresion exp = this.expMul(li);
		return this.expAd2(exp,li);
	}

	/**
	 * <ExpAd2> ::=<OpAd><ExpMul><ExpAd2> | vacio	
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 * @throws ExceptionSemanticoDeclaracion
	 */
	private NExpresion expAd2(NExpresion exp0, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es OpAd
		if(comp("+") || comp("-")){
			Token t = token;
			this.opAd();
			NExpresion exp1 = this.expMul(li);
			NExpresion exp2 = new NExpBinaria(t,exp0,exp1);
			return this.expAd2(exp2,li);
		
		//es vacio
		}else if(comp("==") || comp("!=") || comp("<") || comp(">") || comp(">=") ||
				comp("<=") || comp("||") || comp(")") || comp("]") || comp(";") ||
				comp(",") || comp("&&")){
			//termine
			return exp0;
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());	
		}
		
	}
	
	/**
	 * <ExpMul> ::=<ExpUn><ExpMul2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 * @throws ExceptionSemanticoDeclaracion
	 */
	private NExpresion expMul(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		NExpresion exp = this.expUn(li);
		return this.expMul2(exp,li);
	}

	/**
	 * <ExpMul2> ::=<OpMul><ExpUn><ExpMul2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expMul2(NExpresion exp0, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es opMul
		if(comp("*") || comp("/")){
			Token t = token;
			this.opMul();
			NExpresion exp1 = this.expUn(li);
			NExpresion exp2 = new NExpBinaria(t,exp0,exp1); 
			return this.expMul2(exp2,li);
		
		//es vacio	
		}else if(comp("+") || comp("-") || comp("<") || comp(">") || comp(">=") || comp("<=") 
				|| comp("||") || comp(")") || comp("]") || comp(";") || comp(",") || comp("&&") 
				|| comp("==") || comp("!=")){
			
			//termino
			return exp0;
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());	
		}
		
	}

	/**
	 * <OpIg> ::=    == | !=
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void opIg() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		// es ==
		if(comp("==")){
			match("==");
		
		// es !=
		}else if(comp("!=")){
			match("!=");
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un '==' o '!='", token.getColumna(), token.getLinea());
		}
		
	}
	
	/**
	 * <OpAd> ::= + | -
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void opAd() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		if(comp("+")){
			match("+");
		}else if(comp("-")){
			match("-");
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '+' o '-'", token.getColumna(), token.getLinea());
		}
		
	}

	
	/**
	 * <OpMul> ::= * | /
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void opMul() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		if(comp("*")){
			match("*");
		}else if(comp("/")){
			match("/");
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '*' o '/'", token.getColumna(), token.getLinea());
		}
	}
	
	private void opComp() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		if(comp("<")){
			match("<");
		
		}else if(comp(">")){
			match(">");
		
		}else if(comp("<=")){
			match("<=");
		
		}else if(comp(">=")){
			match(">=");
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '<', '>', '<=' o '>='", token.getColumna(), token.getLinea());
		}
	}

	/**
	 * <ExpUn> ::= <OpUn><ExpUn> | <Operando>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion expUn(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es <opUn>
		if(comp("+") || comp("-") || comp("!")){
			Token t = token;
			this.opUn();
			return new NExpUnaria(t,this.expUn(li));
		
		//es <Operando>
		}else if(comp("null") || comp("true") || comp("false") || comp("entero")
				|| comp("caracter") || comp("string") || comp("(") || comp("this")
				|| comp("IdMetVar") || comp("IdClase") || comp("new")){
			
			return this.operando(li);
		
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '+', '-', '!', un literal o un primario", token.getColumna(), token.getLinea());
		}		
	}

	/**
	 * <Operando> ::= <Literal> | <Primario> 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NOperando operando(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es literal
		if(comp("null") || comp("true") || comp("false") || comp("entero") || 
				comp("caracter") || comp("string")){
			
			return this.literal();
		
		//es primario	
		}else if(comp("(") || comp("this") || comp("IdMetVar") || comp("IdClase") 
				|| comp("new")){
			
			return this.primario(li);
		
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un literal o primario", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <Literal> ::=null | true | false | entero| caracter| string
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NLiteral literal() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Tipo t;		
		Token tk = token;		
		if(comp("null")){
			t = new TipoNull();
			match("null");		
			
		}else if(comp("true")){
			t = new TipoBoolean(true);
			match("true");
			
		}else if(comp("false")){
			t = new TipoBoolean(false);
			match("false");
	
		}else if(comp("entero")){
			t = new TipoInt();
			match("entero");
		
		}else if(comp("caracter")){
			t = new TipoChar();
			match("caracter");
		
		}else if(comp("string")){
			t = new TipoString();
			match("string");
			
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un literal", token.getColumna(), token.getLinea());
		}
		return new NLiteral(tk,t);
	}

	/**
	 * <OpUn> ::= + | - | !
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void opUn() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		if(comp("+")){
			match("+");
		}else if(comp("-")){
			match("-");
		}else if(comp("!")){
			match("!");
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '+', '-' o '!'", token.getColumna(), token.getLinea());
		}
	}
	
	/**
	 * <SentenciaLlamada> ::= ( <Primario> )
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NSentenciaLlamada sentenciaLlamada() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		Token t = token;
		match("(");
		NPrimario prim = this.primario(false);
		match(")");
		return new NSentenciaLlamada(t,prim);
	}
	
	
	/**
	 * <Primario> ::= <ExpresionParentizada> | <AccesoThis> | idMetVar <F4> | 
	 * 					<LlamadaMetodoEstatico> | new <F9>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NPrimario primario(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es <ExpresionParentizada>
		if(comp("(")){
			
			return this.expresionParentizada(li);			
				
		//es <AccesoThis>
		}else if(comp("this")){
			
			return this.accesoThis(li);
			
		//es IdMetVar
		}else if(comp("IdMetVar")){
			Token t = token;
			match("IdMetVar");
			return this.f4(t,li);
			
		//es <LlamadaMEtodoEncadenado>
		}else if(comp("IdClase")){
			
			return this.llamadaMetodoEstatico(li);
		
		//es new
		}else if(comp("new")){
			
			match("new");
			
			return this.f9(li);
		
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba primario", token.getColumna(), token.getLinea());
		}
	}

	/**
	 * <F9> ::= idClase<ArgsActuales><Encadenado> | <TipoPrimitivo>[ <Expresion>] <Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NLlamadaCtor f9(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es idClase
		if(comp("IdClase")){
			Token t = token;
			match("IdClase");
			NArgsActuales args = this.argsActuales(li);
			NEncadenado enc = this.encadenado(t,li);
			
			return new NLlamadaCtorClase(t,new TipoIdClase(t.getLexema()), args,enc);
		
		//es tipo primitivo
		}else if(comp("boolean") || comp("char") || comp("int")){
			Token t = token;
			TipoPrimitivo tipo = this.tipoPrimitivo();
			match("[");
			NExpresion exp = this.expresion(li);
			match("]");
			NEncadenado enc = this.encadenado(t,li);
		
			return new NLlamadaCtorArreglo(t,tipo,exp,enc);
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un identificador de clase o un tipo primitivo", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <TipoPrimitivo> ::= boolean | char | int 
	 * @return 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private TipoPrimitivo tipoPrimitivo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		if(comp("boolean")){
			match("boolean");
			return new TipoBoolean();
		}else if(comp("int")){
			match("int");
			return new TipoInt();
		
		}else if(comp("char")){
			match("char");
			return new TipoChar();
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un tipo primitivo", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <ArgsActuales> ::= (<ListaExpAux>) 
	 * @return 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NArgsActuales argsActuales(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("(");
		LinkedList<NExpresion> lista =  this.argsActualesAux(new LinkedList<NExpresion>(), li);
		match(")");	
		return new NArgsActuales(t,lista);
	}

	/**
	 * <ArgsActualesAux>::= <ListaExp> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<NExpresion> argsActualesAux(LinkedList<NExpresion> lista, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es <ListaExp>
		if(comp("+") || comp("-") || comp("!") || comp("null") ||  comp("true") || comp("false") ||
				 comp("entero") ||  comp("caracter") || comp("string") || comp("(") ||
				 comp("this") || comp("IdMetVar") || comp("IdClase") || comp("new")){
			
			return this.listaExps(lista, li);
			
		//termino	
		}else if(comp(")")){
			//vacio
			return lista;
			
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba una expresión o un ')'. ", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <ListaExps> ::=  <Expresion> <F5>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<NExpresion> listaExps(LinkedList<NExpresion> lista, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		NExpresion exp = this.expresion(li);
		lista.add(exp);
		return this.f5(lista, li);		
	}

	/**
	 * <F5> ::= ,<ListaExps> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<NExpresion> f5(LinkedList<NExpresion> lista, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {

		if(comp(",")){
			match(",");
			return this.listaExps(lista, li);
	
		//vacio
		}else if(comp(")")){
			//termino
			return lista;
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba ',' o ')'", token.getColumna(), token.getLinea());

		}
		
	}

	/**
	 * <LlamadaMetodoEstatico> ::= idClase . <LlamadaMetodo>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NLlamadaMetodoEstatico llamadaMetodoEstatico(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("IdClase");
		match(".");
		NLlamadaMetodo llamadaMetodo = this.llamadaMetodo(li);	
		
		return new NLlamadaMetodoEstatico(t,new TipoIdClase(t.getLexema()),llamadaMetodo, new NEncadenadoVacio(token));		
	}

	/**
	 * <LlamadaMetodo> ::=idMetVar<ArgsActuales><Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NLlamadaMetodo llamadaMetodo(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("IdMetVar");
		NArgsActuales args = this.argsActuales(li);
		NEncadenado enc = this.encadenado(t,li);
		
		return new NLlamadaMetodo(t,args,enc,li,estoyEnAtr);
	}

	/**
	 * <F4> ::= <ArgsActuales><Encadenado> | <Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NPrimario f4(Token t, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es args actuales
		if(comp("(")){
			
			NArgsActuales args = this.argsActuales(li);
			NEncadenado enc = this.encadenado(t,li);
			
			return new NLlamadaMetodo(t,args,enc,li,estoyEnAtr);
			
		//es encadenadp
		}else if(comp("=") || comp(")") || comp("*") || comp("/") || comp("+") || comp("-") ||
				 comp("<") || comp(">") || comp("<=") || comp(">=") || comp("||") || comp(";")
				 || comp("]") || comp(",") || comp("&&") || comp(".") || comp("[") || comp("==") 
				 || comp("!=")){
			
			NEncadenado enc = this.encadenado(t,li);
			return new NAccesoVar(t,enc, li,estoyEnAtr);
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Expresión primaria mal formada", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <ExpresionParentizada> ::= ( <Expresion> ) <Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private  NExpresionParentizada expresionParentizada(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("(");
		NExpresion exp = this.expresion(li);
		match(")");
		NEncadenado enc = this.encadenado(t,li);	
		
		return new NExpresionParentizada(t,exp,enc);
	}

	/**
	 * <AccesoThis> ::=this<Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NAccesoThis accesoThis(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("this");
		NEncadenado enc = this.encadenado(t,li);
		return new NAccesoThis(t,enc,li,estoyEnAtr);	
	}

	/**
	 * <AccesoVar> ::= idMetVar<Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NAccesoVar accesoVar(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("IdMetVar");
		NEncadenado enc = this.encadenado(t,li);
		return new NAccesoVar(t,enc,li,estoyEnAtr);
	}

	/**
	 * <Encadenado> ::=  .<F6> | <AccesoArregloEncadenado> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NEncadenado encadenado(Token t, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es .<f6>
		if(comp(".")){
			match(".");
			return this.f6(li);
		
		//es accesoArregloEncadenado	
		}else if(comp("[")){

			return this.accesoArregloEncadenado(t,li);
		
		//es vacio
		}else if(comp("=") || comp(")") || comp("*") || comp("/") || comp("+") || comp("-") ||
				 comp("<") || comp(">") || comp("<=") || comp(">=") || comp("||") || comp(";")
				 || comp(",") || comp("&&") || comp("==") || comp("!=") || comp("]")){
			
			//termino
			return new NEncadenadoVacio(token);
			
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Expresión encadenada mal formada", token.getColumna(), token.getLinea());
		}
		
	}
	

	/**
	 * <AccesoArregloEncadenado> ::= [<Expresion>]<Encadenado>
	 * @return 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NAccesoArregloEncadenado accesoArregloEncadenado(Token tk, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("[");
		NExpresion exp = this.expresion(li);
		match("]");
		NEncadenado enc = this.encadenado(tk,li);	
		
		return new NAccesoArregloEncadenado(t,exp,enc,li);
	}

	/**
	 * <F6> ::=  idMetVar <F7> 
	 * @param li 
	 * @return 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NEncadenado f6(boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		Token t = token;
		match("IdMetVar");
		return this.f7(t,li);
		
	}
	
	/**
	 * <F7> ::= <ArgsActuales><Encadenado> | <Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NEncadenado f7(Token t, boolean li) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es args actuales
		if(comp("(")){
			
			NArgsActuales args = this.argsActuales(li);
			NEncadenado enc = this.encadenado(t,li);
			
			return new NLlamadaMetodoEncadenado(t,args,enc,li);
			
		//es encadenadp
		}else if(comp("=") || comp(")") || comp("*") || comp("/") || comp("+") || comp("-") ||
				 comp("<") || comp(">") || comp("<=") || comp(">=") || comp("||") || comp(";")
				 || comp("]") || comp(",") || comp("&&") || comp(".") || comp("[") || comp("==") 
				 || comp("!=")){
			
			
			NEncadenado enc = this.encadenado(t,li);
			return new NAccesoVarEncadenado(t,enc,li);
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Expresión primaria mal formada", token.getColumna(), token.getLinea());
		}
		
	}
	
	

	/**
	 * <Atributo> ::= <Visibilidad><Tipo><ListaDecVars> ;
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void atributo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		boolean publico = this.visibilidad();
		Tipo tipo = this.tipo();
		LinkedList<EntradaAtributo> atrs = this.listaDecVars(new LinkedList<EntradaAtributo>(),publico,tipo);
		
		/***SEMANTICO */
		try{
		
			Principal.ts.addAtributos(atrs);
		
		}catch(ExceptionAtributo e){
			System.out.println(e.getMessage());
			
			this.saltearAtributoAsignacion();
			Principal.ts.setHayErrores();
			
			return;
		}
		/***/
		NExpresion exp = this.inline();
		
		for(EntradaAtributo atr: atrs)
			atr.addExpresion(exp);
		
		
		//consumo un ;
		match(";");
		

	}
	
	/**
	 * <InLine> ::= = <Expresion> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private NExpresion inline() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {

		//es =
		if(comp("=")){
			match("=");
			return this.expresion(false);
		
		//es ;	
		}else if(comp(";")){
			//termino
			return new NExpresionVacia(token);
			
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un '=' o ';'", token.getColumna(), token.getLinea());
		}
		
		
	}

	/**
	 * <ListaDecVars> ::= idMetVar <F2> 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<EntradaAtributo> listaDecVars(LinkedList<EntradaAtributo> linkedList, boolean publico, Tipo tipo) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		
		/** SEMANTICO */
		linkedList.add(new EntradaAtributo(publico,tipo,token));
		/***/
		
		//consumo un idMetVar
		match("IdMetVar");
			
		//voy a F2
		return this.f2(linkedList, publico, tipo);
		
		
			
	}
	
	
	/**
	 * <F2> ::=  ,<ListaDecVars> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<EntradaAtributo> f2(LinkedList<EntradaAtributo> linkedList, boolean publico, Tipo tipo) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		//verifico si sigue una ,
		if(comp(",")){
			
			//consumo una coma
			match(",");
				
			return this.listaDecVars(linkedList,publico, tipo);
			
		//verifico si hay un punto y coma o igual
		}else if(comp("=")|| comp(";")){
			
			//termino
			
			return linkedList;
	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba una ',' o ';'", token.getColumna(), token.getLinea());
		}
		
	}
	/**
	 * <Tipo> ::= boolean<F10> | char <F10> | int <F10>  | idClase | String 
	 * @throws ExceptionSintactico
	 * @throws ExceptionLexico
	 */
	private Tipo tipo() throws ExceptionSintactico, ExceptionLexico, ExceptionSemanticoDeclaracion{
		Tipo tipo = null;
		
		//verifico si es un boolean
		if(comp("boolean")){
			
			/** SEMANTICO*/
			tipo = new TipoBoolean();
			/***/			
			
			//consumo boolean
			match("boolean");	
			tipo = f10(tipo);
			
		}else if(comp("char")){
			
			/** SEMANTICO*/
			tipo = new TipoChar();
			/***/		
			
			//consumo char
			match("char");	
			tipo = f10(tipo);
			
		}else if(comp("int")){
			
			/** SEMANTICO*/
			tipo = new TipoInt();
			/***/	
			
			//consumo int
			match("int");	
			tipo = f10(tipo);

			
		}else if(comp("IdClase")){
			
			/** SEMANTICO*/
			tipo = new TipoIdClase(token.getLexema());
			/***/	
			
			//consumo id clase
			match("IdClase");
			
		}else if(comp("String")){
			
			/** SEMANTICO*/
			tipo = new TipoString();
			/***/	
			
			//consumo string
			match("String");
			
		}else{
			
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba las palabras reservadas boolean, char, int o String, o un identificador de clase", token.getColumna(), token.getLinea());
		}	
		
		return tipo;
	}
	
	/**
	 * <F10> ::= [] | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private Tipo f10(Tipo tipo) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		//busco un [
		if(comp("[")){
			
			match("[");
			match("]");
			
			//veo que tipo de arreglo es
			if(tipo instanceof TipoInt)
				return new TipoArregloInt();
			else if(tipo instanceof TipoChar)
				return new TipoArregloChar();
			else if(tipo instanceof TipoBoolean)
				return new TipoArregloBoolean();
			
			return tipo;
				
		//busco idMetVar	
		}else if(comp("IdMetVar")){
			//termino			
			
			return tipo;
			
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '[' o un identificador de metodo o variable", token.getColumna(), token.getLinea());
		}
		
	}
	
	
	/**
	 * <Visibilidad> ::= public | prívate
	 * @return retorna true si es publico, false si es privado
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 * @throws ExceptionSemanticoDeclaracion
	 */
	private boolean visibilidad() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		//me fijo si es public
		if(comp("public")){
			
			//consumo public
			match("public");
			return true;
		
		//me fijo si es private
		}else if(comp("private")){
			
			//consumo private
			match("private");
			return false;
			
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba 'public' o 'private'", token.getColumna(), token.getLinea());
		}
		
	}
	
	/**
	 * <Herencia> : := VACIO | extends idClase
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void herencia() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//me fijo si sigue extends
		if(comp("extends")){
			
			//consumo extends
			match("extends");
			
			/** SEMANTICO */			
			Principal.ts.getClaseActual().setHerencia(token);
			/***/
			
			//consumo IdClase
			match("IdClase");								
		
		//me fijo si sigue {			
		}else if(comp("{")){
			//termine
			
			/** SEMANTICO */			
			Principal.ts.getClaseActual().setHerenciaObject();
			/***/
			
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un '{' o 'extends'.", token.getColumna(), token.getLinea());
		}		
	}
	
	
	
	/**
	 * <MasClase>::= <Clase><MasClase> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 * @throws ExceptionSemanticoDeclaracion 
	 */
	private void masClase() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion, ExceptionSemanticoDeclaracion{
		//termino mas clase?
		if(comp("class")){
			this.clase();
			this.masClase();
			
		//verifico si class es el siguiente	
		}else if(comp("eof")){
			//termino
		}else{
			throw new ExceptionSintactico(token.getLexema(),"No se encontró la palabra reservada 'class'.", token.getColumna(), token.getLinea());
		}
		
	}
	
	
	/**
	 * Retorna true si tipo es igual al tipo del token actual
	 * @param tipo Tipo de token a comparar
	 * @return True si el tipo de token actual coincide con tipo
	 */
	private boolean comp(String tipo){
		return this.token.getTipo().equals(tipo);
	}
	
	
	/**
	 * Recibe un tipo de token, si es igual al tipo de token actual, pide el proximo token, sino lanza una excepcion
	 * @param tipo String es un tipo de token.
	 * @return Retorna true si el tipo es igual al tipo del token actual
	 * @throws ExceptionLexico Se encuentra un error lexico en el programa
	 * @throws ExceptionSintactico 
	 */
	private void match(String tipo) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		if(this.token.getTipo().equals(tipo)){
			this.token = alex.getNextToken();
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba '"+getNombreProlijo(tipo)+"'", token.getLinea(), token.getColumna());
		}
	} 
	
	/**
	 * Recibe un tipo de token y emprolija el nombre, por ejemplo cambia idClase por identificador de clase
	 * @param tipo
	 * @return
	 */
	private String getNombreProlijo(String tipo){
		
		if(tipo.equals("IdMetVar")){
			
			return "identificador de método o variable";
		}else if(tipo.equals("IdClase")){
			
			return "identificador de clase";
		}else if(tipo.equals("eof")){
			
			return "fin de archivo";
		}else{
			
			return tipo;
		}
			
	}
	
	/**
	 * Setea el proximo token del lexico
	 * @throws ExceptionLexico Se encuentra un error lexico en el programa
	 */
	private void setNextToken() throws ExceptionLexico{
		this.token = alex.getNextToken();
	}

	/**
	 * RECUPERACION DE ERRORES
	 */
	
	
	/**
	 * ERROR SEMANTICO DE CLASE NO EXISTE
	 * @throws ExceptionLexico
	 * @throws ExceptionSemanticoDeclaracion 
	 * @throws ExceptionSintactico 
	 */
	private void saltearClaseExiste() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		while(!this.token.getLexema().equals("class") && !this.token.getTipo().equals("eof")){
			this.token = alex.getNextToken();
		}
		if(this.token.getTipo().equals("eof"))
			return;
	}
	
	private void saltearBloque() throws ExceptionLexico{
		int llaves = 1;
		
		this.setNextToken();
		
		while(llaves>0 && comp("}")){
			if(comp("{"))
				llaves++;
			else if(comp("}"))
				llaves--;
			
			this.setNextToken();
		}
		
		return;		
	}
	
	private void saltearAtributoAsignacion() throws ExceptionLexico{
		while(!comp(";")){
			this.setNextToken();
		}
		this.setNextToken();
		return;
	}
	
	private void saltearParametros() throws ExceptionLexico{
		while(!comp("{")){
			this.setNextToken();			
		}
		
	}
	
}
