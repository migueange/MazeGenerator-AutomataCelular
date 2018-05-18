import java.util.Random;

/**
 * Clase que modela un Autómata Celular.
 * @version 1.0
 * @author Miguel
 * @author Hugo
 * @author Victor
 */
public class AutomataCelular {
	
	/*Tablero del autómata, true si la célula está viva, false en otro caso*/
	private boolean [][] automata;
	/*Dimensiones del tablero*/
	private int n,m;

	/**
	 * Construye un autómate de nxm con celulas 
	 * vivas aleatorias en el centro del tablero.
	 * @param n Número de filas
	 * @param m Número de columnas
	 * @param probabilidad Probabilidad de que en el estado inicial, una célula esté viva o no.
	 */
	public AutomataCelular(int n, int m, double probabilidad){
		/*Iniciar variables*/
		this.n = n > 9? n:20;
		this.m = m > 9? m: 20;
		automata = new boolean[n][m];
		Random r = new Random();
		double p = (probabilidad > 0.0 && probabilidad <= 1.0)? probabilidad:0.5;
		/*LLenar centro del autómata con células vivas de forma aleatoria en el centro de la matriz*/
		for(int i = (n/2) - (n/8);i < (n/2) + (n/8);i++)
			for (int j = (m/2) - (m/8);j < (m/2) + (m/8);j++ )
				automata[i][j] = r.nextDouble() < p;							
	}


	/**
	 * Evoluciona el autómata dadas las siguientes reglas: <br/>
	 * 1.Una célula nace si tiene exactamente 3 vecinos vivos. <br/>
	 * 2.Una célula sobrevive de una generación a otra si tiene 
	 * entre 1 y 5 vecinos vivos.<br/>
	 * 3.En otro caso, muere.
	 * @param ruleString El tipo de comportamiento del autómata en notación B/S (Birth/Survive).
	 *  Para más información, visite: <a href="http://www.conwaylife.com/w/index.php?title=Rulestring&redirect=no">BS notation</a>
	 */
	public void evoluciona(String ruleString){		
		/* Tablero que contendrá el autómata evolucionado*/
		boolean [][] evolucion = new boolean[n][m];
		for (int i=0;i<n ;i++ ) 
			for (int j=0;j<m ;j++)
				/*Se recorre el tablero actual y se decide si la célula (i,j) vive o muere.*/
				evolucion[i][j] = viveOmuere(i,j,ruleString);
		/*Se actualiza el tablero principal con la evolución*/
		for (int i=0;i<n ;i++ ) 
			for (int j=0;j<m ;j++)
				automata[i][j] = evolucion[i][j];							
	}

	/**
	 * Cuenta los vecinos vivos de la célula (x,y)
	 * @param x La fila de la célula.
	 * @param y La columna de la célula.
	 * @return un entero que nos dice el número de vecinos vivos.
	 */
	private int cuentaVecinosVivos(int x, int y){
		int vecinosVivos = 0;
		for (int i=Math.max(0,x-1); i <= Math.min(n-1,x+1); i++)
			for (int j=Math.max(0,y-1); j<=Math.min(m-1,y+1); j++) {
				/*No se cuenta la célula actual*/
				if(i==x && j==y)
					continue;
				/*Si está viva, aumenta el contador*/
				vecinosVivos+= (automata[i][j])?1:0;
			}
    	return vecinosVivos;
	}

	/**
	* Decide si una célula vive o muere.
	 * Reglas del autómata:<br/>
	 * 1.Una célula nace si tiene exactamente 3 vecinos vivos.<br/>
	 * 2.Una célula sobrevive de una generación a otra si tiene 
	 * entre 1 y 5 vecinos vivos.<br/>
	 * 3.En otro caso, muere.
	 * @param i La fila de la célula.
	 * @param j La columna de la célula.
	 * @param ruleString El tipo de comportamiento del autómata en notación B/S. Para más información, visite: http://www.conwaylife.com/w/index.php?title=Rulestring&redirect=no
	 * @return True si vive, false si muere.
	 * @throws UnsupportedOperationException si no está implementada la ruleString dada.
	 */
	private boolean viveOmuere(int i, int j, String ruleString) throws UnsupportedOperationException{
		int vecinosVivos = cuentaVecinosVivos(i,j);
		/*Se pueden implementar más ruleStrings*/
		switch(ruleString){	
			/*Autómata Mazectric*/		
			case "B3/S1234":
				if(vecinosVivos > 0 && vecinosVivos < 5 && automata[i][j])
					return true;				
				if(vecinosVivos == 3 && !automata[i][j])
					return true;
				return false;				
			/*Autómata Maze*/
			case "B3/S12345":
				if(vecinosVivos > 0 && vecinosVivos < 6 && automata[i][j])
					return true;
				if(vecinosVivos == 3 && !automata[i][j])
					return true;
				return false;
			/*Autómata de conway's "Game of life", el más famoso de los autómatas célulares*/
			case "B3/S23":
				if((vecinosVivos == 3 || vecinosVivos == 2) && automata[i][j])
					return true;
				if(vecinosVivos == 3 && !automata[i][j])
					return true;
				return false;
			/*Autómata Seed*/
			case  "B2/S":
				if(vecinosVivos == 2)
					return true;
				return false;
			default:
				throw new UnsupportedOperationException(ruleString + " no es una ruleString válida");
		}		
	}

	/**
	 * Regresa el autómata en su estado actual.
	 * @return Una matriz representando el autómata.
	 */
	public boolean[][] getEstadoActual(){
		return automata;
	}

	/**
	 * Regresa una representación en cadena del autómata, [#] si está viva, [ ] si está muerta.
	 * @return Una representación en cadena del autómata.
	 */
	@Override
	public String toString(){
		String automataString = "[#]: VIVA, [ ]: MUERTA\n";
		for (int i=0;i<n ;i++ ) {
			for (int j=0;j<m ;j++)
				if(automata[i][j])
					automataString+="[#]";
				else
					automataString+="[ ]";							
			automataString+="\n";
		}
		return automataString;
	}



}