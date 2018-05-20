import processing.core.PApplet;
import processing.core.PFont;

/**
 * Interfaz para dibujar autómatas celulares y su evolución.
 * Nos centramos en los autómatas que generan laberintos.
 * @version 1.0
 * @author Miguel
 * @author Hugo
 * @author Victor
 */
public class LaberintoAC extends PApplet{

	/*Número de eoluciones del autómata*/
	private static final int numEvoluciones=200;
	/*Evolución actual*/
	private int evolucionActual=0;
	/*Alto del tablero*/
	private static final int altoTablero=50;
	/*Ancho del tablero*/
	private static final int anchoTablero=50;
	/*Resolución*/
	private static final int resolucion=10;
	/*RuleString*/
	private static final String ruleString = "B2/S123";
	/*El autómata celular*/
	private static final AutomataCelular automata = new AutomataCelular(altoTablero, anchoTablero, 0.25);

	/**
	 * Propiedades de la interfaz.	 
	 */
	@Override
	public void settings(){
		size(anchoTablero*resolucion,(resolucion*altoTablero)+40);
	}

	/** 
	 * Configuracion inicial de la interfaz.
	 */
    @Override
    public void setup(){
    	background(255); 
    	System.out.println("Usando ruleString: " + ruleString);         
    }

    /**
     * Dibuja cada evolución del autómata.
     */
    @Override
    public void draw(){
    	if(evolucionActual == numEvoluciones)  {
    		System.out.println("Se terminó la evolución");
    		noLoop();            
    	}
    	boolean [][] estadoActual = automata.getEstadoActual();    	
    	background(255);          
    	for (int i=0; i<altoTablero; i++)
    		for (int j=0; j<anchoTablero; j++) 
    			if(estadoActual[i][j]){
    				fill(0);
    				rect(j*resolucion,i*resolucion,resolucion,resolucion);
    			}
        text("Evolución: " + evolucionActual,((anchoTablero*resolucion)/2)-45,(altoTablero*resolucion)+23);
    	automata.evoluciona(ruleString);
    	evolucionActual++;
        delay(50);
    }

    /**
     * Main
     */
	public static void main(String[] args) {
		PApplet.main(new String[] { "LaberintoAC" });
	}

}