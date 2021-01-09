




import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Archivo {
	
	/** atributos - array de array es la fila. y cada fila tiene caracteres con su columna */
	private int fila;
	private int columna;
	private char actual;
	private BufferedReader fuente;
	
	/** Constructor de la clase. Paso la ruta del archivo por parametro */
	public Archivo(String ruta) throws FileNotFoundException, IOException{
		this.cargarArchivo(ruta);
		this.fila = 0;
		this.columna = 0;
		this.actual = ' ';		
	}
	
	
	/** Retorna el actual y no mueve el puntero */
	public char getActual(){
		return actual;
	}
	/** Retorna el actual y mueve el puntero 
	 * @throws IOException */
	public char getNext() throws IOException{		

		//tomo el caracter
		this.actual = (char) fuente.read();
		
		//incremento las columnas
		columna++;
		
		//verifico que sea fin de linea
		if(this.actual=='\n'){
			columna = 0;
			fila++;
			
		//verifico q sea fin de archivo	
		}else if(actual==65535){
			this.actual = 0;
	    	fuente.close();
		}	
		return this.actual;	
	}
	

	/** Retorna la linea actual dentro del archivo */
	public int getLinea(){
		return this.fila+1;
	}
	
	/** Retorna la posicion actual dentro de la fila */
	public int getColumna(){
		return this.columna;
	}
	
	
	/** Abre el archivo, y pasa su contenido a una lista de char */
	private void cargarArchivo(String archivo) throws FileNotFoundException, IOException {
		//verifico que exista el archivo
		File file = new File(archivo);
	    if (!file.exists())
	      throw new FileNotFoundException("'"+archivo + "' no existe.");
		
	    //verifico si se puede leer
	    if (!(file.isFile() && file.canRead()))
		      throw new IOException("No tiene permisos para leer: '"+archivo +"'");
 
	    
	    //paso el contenido a una lista de char
	    try {
	    	//abro ela rchivo
	    	FileReader f = new FileReader(file);
	    	fuente = new BufferedReader(f); 
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
}
