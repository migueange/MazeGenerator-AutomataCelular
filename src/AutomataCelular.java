import java.util.Random;

/**
 * Clase que modela un Autómata Celular.
 * Se enfoca principalmente en reglas y técnicas que generan laberintos.
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
	 * Crea un autómata de nxm dado un estado inicial.
	 * @param estadoInicial El estado inicial del autómata.
	 */
	public AutomataCelular(boolean[][] estadoInicial){
		n = estadoInicial.length;
		m = estadoInicial[0].length;
		automata = estadoInicial;
	}

	/**
	 * Evoluciona el autómata una generación dadas las reglas del ruleString.
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
	 * Evoluciona el autómata una generación dadas las reglas del ruleString y una solución dada.
	 * @param ruleString El tipo de comportamiento del autómata en notación B/S (Birth/Survive).
	 *  Para más información, visite: <a href="http://www.conwaylife.com/w/index.php?title=Rulestring&redirect=no">BS notation</a>
	 * @param solucion Una matriz que nos da un camino ya hecho desde un punto A a un punto B. Más allá
	 * del estado inicial, en cada evolución no se modifican las células vivas en la solución dada.
	 * El éxito del laberinto depende de que tan compleja es la solución dada.
	 * @throws IllegalArgumentException Si el tamaño de la mariz solución no coincide con el tamaño del autómata.
	 */
	public void evoluciona(boolean [][] solucion,String ruleString) throws IllegalArgumentException{
		if(n != solucion.length || m != solucion[0].length)
			throw new IllegalArgumentException("La matriz con la solución debe ser de las mismas dimensiones que el autómata");
		/* Tablero que contendrá el autómata evolucionado*/
		boolean [][] evolucion = new boolean[n][m];
		for (int i=0;i<n ;i++ ) 
			for (int j=0;j<m ;j++)
				/*Se recorre el tablero actual y se decide si la célula (i,j) vive o muere.*/
				if(solucion[i][j])//Si la célula actual está marcada como solución, la dejamos viva.
					evolucion[i][j] = true;
				else//Si no es parte de la solución, se decide por las reglas.
					evolucion[i][j] = viveOmuere(i,j,ruleString);
		/*Se actualiza el tablero principal con la evolución*/
		for (int i=0;i<n ;i++ ) 
			for (int j=0;j<m ;j++)
				automata[i][j] = evolucion[i][j];	
	}

	/**
	 * Genera una solución(camino) para un laberinto.
	 * El algoritmo implementado es muy sencillo,  el camino se inicia en la célula(0,0) y se 
	 * termina en la célula(n-1,m-1). La solución se va construyendo con la condición de que solo
	 * se puede avanzar hacia abajo o hacia la derecha, por lo tanto las soluciones son muy sencillas
	 * para el laberinto.
	 * @return boolean[][] Una matriz con el camino solución del laberinto.
	 */
	public boolean[][] generaSolucionLaberinto(){
		boolean [][] solucion = new boolean[n][m];
		int iInicio=0,jInicio=0,iFinal=n-1,jFinal=m-1,i=0,j=0;
		Random r = new Random();
		solucion[i][j]=true;
		boolean termina=false;		
		while(!termina){
			if(r.nextBoolean())//Avanza hacia abajo
				i = Math.min(i+1,n-1);
			else//Avanza derecha
				j = Math.min(j+1,m-1);
			solucion[i][j]=true;	
			termina = i == iFinal && j == jFinal;		
		}		
		return solucion;
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
	* Decide si una célula vive o muere dadas las reglas especificadas por el ruleString.
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
			/*B2/S123*/
			case "B2/S123":
				if(vecinosVivos == 2 && !automata[i][j])
					return true;
				if(vecinosVivos > 0 && vecinosVivos < 4	 && automata[i][j])
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