import processing.core.PApplet;
import processing.core.PFont;

/**
 * @author Miguel Mendoza
 */
public class LaberintoAC extends PApplet{

	/*Número de eoluciones del autómata*/
	private int numEvoluciones=4000;
	/*Evolución actual*/
    private int evolucionActual=0;
	/*Alto del tablero*/
	private int altoTablero=500;
	/*Ancho del tablero*/
	private int anchoTablero=500;
	/*El autómata celular*/
	private AutomataCelular automata = new AutomataCelular(altoTablero, anchoTablero);


	/**/
	@Override
	public void settings(){
		size((anchoTablero),(altoTablero));
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
    				rect(j*2,i*2,2,2);
    			} 			    	
    	automata.evoluciona();
    	evolucionActual++;
    }


	public static void main(String[] args) {
		PApplet.main(new String[] { "LaberintoAC" });

	}

}