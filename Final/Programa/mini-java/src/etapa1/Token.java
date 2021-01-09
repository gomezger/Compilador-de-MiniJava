package etapa1;




public class Token {
	//atributos
	private String tipo;
	private String lexema;
	private int linea;
	private int columna;
	

	//constructor
	public Token(String tipo, String lexema, int linea, int columna){
		this.setLexema(lexema);
		this.setTipo(tipo);
		this.setLinea(linea);
		this.columna = columna;
	}

	//getters and setters
	public String getTipo() {
		return tipo;
	}
	private void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLexema() {
		return lexema;
	}
	private void setLexema(String lexema) {
		this.lexema = lexema;
	}
	public int getLinea() {
		return linea;
	}
	public int getColumna() {
		return columna;
	}
	private void setLinea(int linea) {
		this.linea = linea;
	}
	
}
