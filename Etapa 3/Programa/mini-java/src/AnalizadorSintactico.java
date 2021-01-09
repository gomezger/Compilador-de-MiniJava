

import java.util.LinkedList;

public class AnalizadorSintactico {
	private AnalizadorLexico alex;
	private Token token;

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

			this.atributo();
			this.miembro();
		
		// me fijo si es <Ctor> viendo sus primeros
		}else if(comp("IdClase")){
			
			
			try{	

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
		
		
		
		this.bloque();
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
		
			
		this.bloque();	
		
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
	private void bloque() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		match("{");
		this.masSentencia();
		match("}");
	}
	
	
	/**
	 * <MasSentencia> ::=  <Sentencia><MasSentencia> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void masSentencia() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		//es sentencia
		if(comp(";") || comp("this") || comp("IdMetVar") || comp("(") ||  comp("boolean") || 
			comp("char") || comp("int") || comp("IdClase") || comp("String") || 
			comp("if") || comp("while") || comp("{") || comp("return")){
			
			this.sentencia();
			this.masSentencia();
		
		//termino mas sentencia	
		}else if(comp("}")){
			//termino
		}else{	
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba una '}' o una sentencia válida", token.getColumna(), token.getLinea());
		}
	}
	
	/**
	 * <Sentencia> ::= ; | <Asignacion>; | <SentenciaLlamada>; | <Tipo><ListaDecVars>;  |
	 * 		if( <Expresion> ) <Sentencia> <F3> | while ( <Expresion> ) <Sentencia> | <Bloque> | 
	 * 		return <ExpresionOpcional>;
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void sentencia() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		//es un punto y coma
		if(comp(";")){
			match(";");
		
		//es asignacion	
		}else if(comp("this") || comp("IdMetVar")){
			this.asignacion();
			match(";");		
		
		// es sentencia llamada
		}else if(comp("(")){
			this.sentenciaLlamada();
			match(";");
		// es tipo
		}else if(comp("boolean") || comp("char") || comp("int") || comp("IdClase") || comp("String")){
			this.tipo();
			this.listaDecVars(new LinkedList<EntradaVariable>(),false,null);
			this.inline();
			match(";");
		
		//es if
		}else if(comp("if")){
			match("if");
			match("(");
			this.expresion();
			match(")");
			this.sentencia();
			this.f3();
		
		//es while	
		}else if(comp("while")){
			match("while");
			match("(");
			this.expresion();
			match(")");
			this.sentencia();
		
		//es bloque	
		}else if(comp("{")){
			this.bloque();
		
		//es return
		}else if(comp("return")){
			match("return");
			this.expresionOpcional();
			match(";");
		
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
	private void asignacion() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//ir a acceso var
		if(comp("IdMetVar")){
			this.accesoVar();
			match("=");
			this.expresion();
			
		//ir a acceso this	
		}else if(comp("this")){
			this.accesoThis();
			match("=");
			this.expresion();
		
		//es un error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un identificar de clase o variable, o un 'this'", token.getColumna(), token.getLinea());
		}
	}
	
	/**
	 * <ExpresionOpcional> ::=  <ExpOr> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expresionOpcional() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//entro a <expOr>
		if(comp("+") || comp("-") || comp("!") || comp("null") || comp("true") || comp("false") 
			|| comp("entero") || comp("caracter") || comp("string") || comp("(") || 
			comp("this") || comp("IdClase") || comp("IdMetVar") || comp("new")){
			
			this.expOr();
		
		//es vacio
		}else if(comp(";")){
			//termino
		
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
	private void f3() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
	
		//me fijo si es else
		if(comp("else")){
			match("else");
			this.sentencia();
		
		//me fijo si es vacio
		}else if(comp("}") || comp(";") || comp("IdMetVar") || comp("this") || comp("(")
				|| comp("boolean") || comp("char") || comp("int") || comp("IdClase")||
				comp("String") || comp("if") || comp("while") || comp("{") || comp("return")){
			//termine
		
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
	private void expresion() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		this.expOr();
	}

	
	/**
	 * <ExpOr> ::=<ExpAnd><ExpOr2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expOr() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.expAnd();
		this.expOr2();
	}

	/**
	 * <ExpOr2> ::= || <ExpAnd><ExpOr2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expOr2() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		//es un ||
		if(comp("||")){
			match("||");
			this.expAnd();
			this.expOr2();
		
		//es vacio
		}else if(comp(")") || comp("]") || comp(";") || comp(",")){
			//termino
		
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
	private void expAnd() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.expIg();
		this.expAnd2();
	}

	/**
	 * <ExpAnd2> ::=&&<ExpIg><ExpAnd2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expAnd2() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		// es un &&
		if(comp("&&")){
			match("&&");
			this.expIg();
			this.expAnd2();
		
		//termino
		}else if(comp("||") || comp(")") || comp("]") || comp(";") || comp(",")){			
			//termino
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());
		}
	}

	/**
	 * <ExpIg> ::=<ExpComp><ExpIg2>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expIg() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.expComp();
		this.expIg2();
	}

	/**
	 * <ExpIg2> ::=<OpIg><ExpComp><ExpIg2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expIg2() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es OpIg
		if(comp("==") || comp("!=")){
			this.opIg();
			this.expComp();
			this.expIg2();
		
		}else if(comp("||") || comp(")") || comp("]") || comp(";") || comp(",") || comp("&&")){
			//termino
		
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
	 * <ExpComp> ::=<ExpAd ><F8>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expComp()  throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.expAd();
		this.f8();
	}

	
	/**
	 * <F8> ::=  <OpComp><ExpAd> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void f8() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		// es OpComp
		if(comp("<") || comp(">") || comp(">=") || comp("<=")){
			this.opComp();
			this.expAd();
		
		//es vacio
		}else if(comp("==") || comp("!=") || comp("||") || comp(")") || comp("]") || 
				comp(";") || comp(",") || comp("&&")){
			//termino
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());	
		}
	}

	/**
	 * 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expAd() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.expMul();
		this.expAd2();
	}

	private void expAd2() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es OpAd
		if(comp("+") || comp("-")){
			this.opAd();
			this.expMul();
			this.expAd2();
		
		//es vacio
		}else if(comp("==") || comp("!=") || comp("<") || comp(">") || comp(">=") ||
				comp("<=") || comp("||") || comp(")") || comp("]") || comp(";") ||
				comp(",") || comp("&&")){
			//termine
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());	
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

	private void expMul() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		this.expUn();
		this.expMul2();
	}

	/**
	 * <ExpMul2> ::=<OpMul><ExpUn><ExpMul2> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void expMul2() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es opMul
		if(comp("*") || comp("/")){
			this.opMul();
			this.expUn();
			this.expMul2();
		
		//es vacio	
		}else if(comp("+") || comp("-") || comp("<") || comp(">") || comp(">=") || comp("<=") 
				|| comp("||") || comp(")") || comp("]") || comp(";") || comp(",") || comp("&&") 
				|| comp("==") || comp("!=")){
			
			//termino
			
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"'Expresión mal formada", token.getColumna(), token.getLinea());	
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
	private void expUn() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es <opUn>
		if(comp("+") || comp("-") || comp("!")){
			this.opUn();
			this.expUn();
		
		//es <Operando>
		}else if(comp("null") || comp("true") || comp("false") || comp("entero")
				|| comp("caracter") || comp("string") || comp("(") || comp("this")
				|| comp("IdMetVar") || comp("IdClase") || comp("new")){
			
			this.operando();
		
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
	private void operando() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es literal
		if(comp("null") || comp("true") || comp("false") || comp("entero") || 
				comp("caracter") || comp("string")){
			
			this.literal();
		
		//es primario	
		}else if(comp("(") || comp("this") || comp("IdMetVar") || comp("IdClase") 
				|| comp("new")){
			
			this.primario();
		
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
	private void literal() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		
		if(comp("null")){
			match("null");
			
		}else if(comp("true")){
			match("true");
			
		}else if(comp("false")){
			match("false");
	
		}else if(comp("entero")){
			match("entero");
		
		}else if(comp("caracter")){
			match("caracter");
		
		}else if(comp("string")){
			match("string");
			
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un literal", token.getColumna(), token.getLinea());
		}
		
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
	private void sentenciaLlamada() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		match("(");
		this.primario();
		match(")");
	}
	
	
	/**
	 * <Primario> ::= <ExpresionParentizada> | <AccesoThis> | idMetVar <F4> | 
	 * 					<LlamadaMetodoEstatico> | new <F9>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void primario() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es <ExpresionParentizada>
		if(comp("(")){
			this.expresionParentizada();			
				
		//es <AccesoThis>
		}else if(comp("this")){
			this.accesoThis();
			
		//es IdMetVar
		}else if(comp("IdMetVar")){
			match("IdMetVar");
			this.f4();
			
		//es <LlamadaMEtodoEncadenado>
		}else if(comp("IdClase")){
			this.llamadaMetodoEstatico();
		
		//es new
		}else if(comp("new")){
			match("new");
			this.f9();
		
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
	private void f9() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es idClase
		if(comp("IdClase")){
			match("IdClase");
			this.argsActuales();
			this.encadenado();
		
		//es tipo primitivo
		}else if(comp("boolean") || comp("char") || comp("int")){
			this.tipoPrimitivo();
			match("[");
			this.expresion();
			match("]");
			this.encadenado();
		
		//error	
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un identificador de clase o un tipo primitivo", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <TipoPrimitivo> ::= boolean | char | int 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void tipoPrimitivo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		if(comp("boolean")){
			match("boolean");
		
		}else if(comp("int")){
			match("int");
		
		}else if(comp("char")){
			match("char");
		
		//error
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un tipo primitivo", token.getColumna(), token.getLinea());
		}
		
	}

	/**
	 * <ArgsActuales> ::= (<ListaExpAux>) 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void argsActuales() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {

		match("(");
		this.argsActualesAux();
		match(")");	
		
	}

	/**
	 * <ArgsActualesAux>::= <ListaExp> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void argsActualesAux() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es <ListaExp>
		if(comp("+") || comp("-") || comp("!") || comp("null") ||  comp("true") || comp("false") ||
				 comp("entero") ||  comp("caracter") || comp("string") || comp("(") ||
				 comp("this") || comp("IdMetVar") || comp("IdClase") || comp("new")){
			
			this.listaExps();
			
		//termino	
		}else if(comp(")")){
			//vacio
		
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
	private void listaExps() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		this.expresion();
		this.f5();		
	}

	/**
	 * <F5> ::= ,<ListaExps> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void f5() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {

		if(comp(",")){
			match(",");
			this.listaExps();
	
		//vacio
		}else if(comp(")")){
			//termino
		
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
	private void llamadaMetodoEstatico() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("IdClase");
		match(".");
		this.llamadaMetodo();		
	}

	/**
	 * <LlamadaMetodo> ::=idMetVar<ArgsActuales><Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void llamadaMetodo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("IdMetVar");
		this.argsActuales();
		this.encadenado();
		
	}

	/**
	 * <F4> ::= <ArgsActuales><Encadenado> | <Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void f4() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es args actuales
		if(comp("(")){
			this.argsActuales();
			this.encadenado();
			
		//es encadenadp
		}else if(comp("=") || comp(")") || comp("*") || comp("/") || comp("+") || comp("-") ||
				 comp("<") || comp(">") || comp("<=") || comp(">=") || comp("||") || comp(";")
				 || comp("]") || comp(",") || comp("&&") || comp(".") || comp("[") || comp("==") 
				 || comp("!=")){
			
			
			this.encadenado();
		
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
	private void expresionParentizada() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("(");
		this.expresion();
		match(")");
		this.encadenado();	
	}

	/**
	 * <AccesoThis> ::=this<Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void accesoThis() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("this");
		this.encadenado();		
	}

	/**
	 * <AccesoVar> ::= idMetVar<Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void accesoVar() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("IdMetVar");
		this.encadenado();
		
	}

	/**
	 * <Encadenado> ::=  .<F6> | <AccesoArregloEncadenado> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void encadenado() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		//es .<f6>
		if(comp(".")){
			match(".");
			this.f6();
		
		//es accesoArregloEncadenado	
		}else if(comp("[")){

			this.accesoArregloEncadenado();
		
		//es vacio
		}else if(comp("=") || comp(")") || comp("*") || comp("/") || comp("+") || comp("-") ||
				 comp("<") || comp(">") || comp("<=") || comp(">=") || comp("||") || comp(";")
				 || comp(",") || comp("&&") || comp("==") || comp("!=") || comp("]")){
			
			//termino
			
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Expresión encadenada mal formada", token.getColumna(), token.getLinea());
		}
		
	}
	

	/**
	 * <AccesoArregloEncadenado> ::= [<Expresion>]<Encadenado>
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void accesoArregloEncadenado() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("[");
		this.expresion();
		match("]");
		this.encadenado();		
	}

	/**
	 * <F6> ::=  idMetVar <F7> 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void f6() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		match("IdMetVar");
		this.f4();
		
	}
	
	

	/**
	 * <Atributo> ::= <Visibilidad><Tipo><ListaDecVars> ;
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void atributo() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		boolean publico = this.visibilidad();
		Tipo tipo = this.tipo();
		LinkedList<EntradaVariable> atrs = this.listaDecVars(new LinkedList<EntradaVariable>(),publico,tipo);
		
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
		
		this.inline();
		
		//consumo un ;
		match(";");
		

	}
	
	/**
	 * <InLine> ::= = <Expresion> | vacio
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private void inline() throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion {
		
		//es =
		if(comp("=")){
			match("=");
			this.expresion();
		
		//es ;	
		}else if(comp(";")){
			//termino
		}else{
			throw new ExceptionSintactico(token.getLexema(),"Se esperaba un '=' o ';'", token.getColumna(), token.getLinea());
		}
		
		
	}

	/**
	 * <ListaDecVars> ::= idMetVar <F2> 
	 * @throws ExceptionLexico
	 * @throws ExceptionSintactico
	 */
	private LinkedList<EntradaVariable> listaDecVars(LinkedList<EntradaVariable> linkedList, boolean publico, Tipo tipo) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
		
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
	private LinkedList<EntradaVariable> f2(LinkedList<EntradaVariable> linkedList, boolean publico, Tipo tipo) throws ExceptionLexico, ExceptionSintactico, ExceptionSemanticoDeclaracion{
		
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
