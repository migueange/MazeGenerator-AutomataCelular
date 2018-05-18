import processing.core.PApplet;
import processing.core.PFont;

/**
 * @author Miguel Mendoza
 */
public class LaberintoAC extends PApplet{

	/*Número de eoluciones del autómata*/
	private int numEvoluciones=200;
	/*Evolución actual*/
    private int evolucionActual=0;
	/*Alto del tablero*/
	private int altoTablero=100;
	/*Ancho del tablero*/
	private int anchoTablero=100;
    /*Resolución*/
    private int resolucion=5;
	/*El autómata celular*/
	private AutomataCelular automata = new AutomataCelular(altoTablero, anchoTablero, 0.25);


	/**/
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
    }

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
    	automata.evoluciona("B3/S1234");
    	evolucionActual++;
        delay(50);
    }


	public static void main(String[] args) {
		PApplet.main(new String[] { "LaberintoAC" });

	}

}