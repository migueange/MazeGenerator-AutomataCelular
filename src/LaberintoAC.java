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
	private static final int altoTablero=70;
	/*Ancho del tablero*/
	private static final int anchoTablero=70;
    /*Resolución*/
    private static final int resolucion=10;
    /*RuleString*/
    private static final String ruleString = "B3/S12345";
	/*El autómata celular*/
	private static final AutomataCelular automata = new AutomataCelular(altoTablero, anchoTablero, 0.25);
    /*Una solución del laberinto*/
	private static final boolean [][] solucion = automata.generaSolucionLaberinto();

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
    	background(0); 
    	noStroke();
    	System.out.println("Usando ruleString: " + ruleString);         
    }

    /**
     * Dibuja cada evolución del autómata.
     */
    @Override
    public void draw(){
    	if(evolucionActual == numEvoluciones+1)  {
    		System.out.println("Se terminó la evolución");
    		delay(5000);
    		if(solucion != null)
    			for (int i=0; i<altoTablero; i++)
    				for (int j=0; j<anchoTablero; j++)     			
    					if(solucion[i][j]){
    						fill(0,255,0);    				
    						rect(j*resolucion,i*resolucion,resolucion,resolucion);
	  					}   				            
	  		noLoop();
    	}else{
    	boolean [][] estadoActual = automata.getEstadoActual();    	
    	background(0);          
    	for (int i=0; i<altoTablero; i++)
    		for (int j=0; j<anchoTablero; j++)     			
   				if(estadoActual[i][j]){
   					fill(255);    				
   					rect(j*resolucion,i*resolucion,resolucion,resolucion);
   				}
        text("Evolución: " + evolucionActual,((anchoTablero*resolucion)/2)-45,(altoTablero*resolucion)+23);
        if(solucion != null)
    		automata.evoluciona(solucion,ruleString);
    	else 
    		automata.evoluciona(ruleString);
    	evolucionActual++;
        delay(50);
    	}
    	//noLoop();
    }

    /** Indica que se desea expandir el siguiente 
     * nivel al hacer click, se deben agregar loop() en este método
     * y noLoop() en draw(). 
     */
    @Override
    public void mouseClicked() {
        //loop();
    }

    /**
     * Main
     */
	public static void main(String[] args) {
		PApplet.main(new String[] { "LaberintoAC" });
	}

}