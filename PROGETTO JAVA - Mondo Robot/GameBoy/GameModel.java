package GameBoy;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
import java.util.Random;

import Componenti.AnimaleDomestico;
import Componenti.Cella;
import Componenti.Fornello;
import Componenti.Lavatrice;
import Componenti.Muro;
import Componenti.Robot;
import Componenti.Rubinetto;
import Componenti.Vuota;

//Gestisco i dati
public class GameModel {

	public Cella[][] grigliaInferiore; // presenta tutti gli elementi e i tipi
	private int dimensione;

	public Robot gino;
	public AnimaleDomestico cane;

	/**
	 * Costruttore
	 * @param dim
	 */
	public GameModel(int dim) {
		this.dimensione = dim;
		grigliaInferiore = new Cella[dimensione][dimensione];
		creaGriglia();
	}

	/**
	 * Inizializza la griglia inferiore a vuota
	 */
	public void creaGriglia() {
		for (int i = 0; i < dimensione; i++) {
			for (int j = 0; j < dimensione; j++) {
				this.grigliaInferiore[i][j] = new Vuota(i, j, false);
			}
		}
	}
	
	/**
	 * Restituisce la dimensione N della mappa
	 * @return int
	 */
	public int getDimensione() {
		return dimensione;
	}

	/**
	 * Restituisce la grigliaInferiore
	 * @return Cella[][]
	 */
	public Cella[][] getGrigliaInferiore() {
		return grigliaInferiore;
	}
	
	/**
	 * Questo metodo inizializza il rubinetto 
	 * @param r
	 * @param c
	 * @param passato
	 * @param stato
	 */
	public void inizializzaRubinetto(int r, int c, boolean passato, boolean stato) {
		if (grigliaInferiore[r][c] instanceof Vuota) {
			Rubinetto rubinetto = new Rubinetto(r, c, passato, stato);
			grigliaInferiore[r][c] = rubinetto;
		}
	}
	/**
	 * Questo metodo inizializza la lavatrice
	 * @param r
	 * @param c
	 * @param passato
	 * @param stato
	 */
	public void inizializzaLavatrice(int r, int c, boolean passato, boolean stato) {
		if (grigliaInferiore[r][c] instanceof Vuota) {
			Lavatrice lavatrice = new Lavatrice(r, c, passato, stato);
			grigliaInferiore[r][c] = lavatrice;
		}
	}
	/**
	 * Questo metodo inizializza il fornello
	 * @param r
	 * @param c
	 * @param passato
	 * @param stato
	 */
	public void inizializzaFornello(int r, int c, boolean passato, boolean stato) {
		if (grigliaInferiore[r][c] instanceof Vuota) {
			Fornello fornello = new Fornello(r, c, passato, stato);
			grigliaInferiore[r][c] = fornello;
		}
	}
	/**
	 * Questo il perimetro MURO della mappa
	 * @param N
	 */
	public void inizializzaMuri(int N) {
		for (int i = 0; i < N; i++) {
			grigliaInferiore[0][i] = new Muro(0, i);
			grigliaInferiore[N - 1][i] = new Muro(N - 1, i);

			for (int j = 0; j < N; j++) {
				grigliaInferiore[j][0] = new Muro(j, 0);
				grigliaInferiore[j][N - 1] = new Muro(j, N - 1);
			}
		}
	}
	/**
	 * Questo metodo inizializza il robot
	 * @param r
	 * @param c
	 */
	public void inizializzaRobot(int r, int c) {
		if (grigliaInferiore[r][c] instanceof Vuota) {
			this.gino = new Robot(r, c);
			settaTrueTutteLeCelleAdiacentiARobot();
		}
	}
	/**
	 * Questo metodo inizializza l'animaleDomestico
	 * @param r
	 * @param c
	 */
	public void inizializzaAnimaleDomestico(int r, int c) {
		if (grigliaInferiore[r][c] instanceof Vuota) {
			this.cane = new AnimaleDomestico(r, c);
		}
	}
	/**
	 * Questo metodo setta una cella vuota a bagnata
	 * @param r
	 * @param c
	 */
	public void setBagnata(int r, int c) {
		((Vuota) grigliaInferiore[r][c]).setBagnata();
	}
	/**
	 * Questo metodo setta una cella vuota ad asciutta
	 * @param r
	 * @param c
	 */
	public void setAsciutta(int r, int c) {
		((Vuota) grigliaInferiore[r][c]).setAsciutta();
	}
	/**
	 * Questo metodo definisce se la mossa del robot è possibile 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean muoviRobot(int x, int y) {
		int rr = gino.getR();
		int cc = gino.getC();

		if (((x != cane.getR()) || (y != cane.getC())) && ((rr == x && cc == y - 1) || (rr == x && cc == y + 1)
				|| (rr == x + 1 && cc == y) || (rr == x - 1 && cc == y)) && grigliaInferiore[x][y] instanceof Vuota) {
			gino.setR(x);
			gino.setC(y);
			settaTrueTutteLeCelleAdiacentiARobot();
			return true;
		}
		return false;
	}
	/**
	 * Questo metodo definisce se la mossa dell' animaleDomestico è possibile 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean muoviAnimaleDomestico(int x, int y) {
		int rr = cane.getR();
		int cc = cane.getC();
		if (((x != gino.getR()) || (y != gino.getC())) && ((rr == x && cc == y - 1) || (rr == x && cc == y + 1)
				|| (rr == x + 1 && cc == y) || (rr == x - 1 && cc == y)) && grigliaInferiore[x][y] instanceof Vuota) {
			cane.setR(x);
			cane.setC(y);
			return true;
		}
		return false;
	}
	
	/**
	 * Questo metodo setta le celle visitate dal robot
	 */

	public void settaTrueTutteLeCelleAdiacentiARobot() {
		int rr = gino.getR();
		int cc = gino.getC();
		grigliaInferiore[rr][cc].setPassato();
		grigliaInferiore[rr - 1][cc].setPassato();
		grigliaInferiore[rr + 1][cc].setPassato();
		grigliaInferiore[rr][cc + 1].setPassato();
		grigliaInferiore[rr][cc - 1].setPassato();
	}
	/**
	 * Questo metodo restituisce un fornello se è adiacente al robot
	 * @param r
	 * @param c
	 * @return Cella
	 */
	public Cella trovaFornello(int r, int c) {
		if (grigliaInferiore[r - 1][c] instanceof Fornello)
			return grigliaInferiore[r - 1][c];
		if (grigliaInferiore[r + 1][c] instanceof Fornello)
			return grigliaInferiore[r + 1][c];
		if (grigliaInferiore[r][c + 1] instanceof Fornello)
			return grigliaInferiore[r][c + 1];
		if (grigliaInferiore[r][c - 1] instanceof Fornello)
			return grigliaInferiore[r][c - 1];
		return null;
	}
	/**
	 * Questo metodo restituisce un rubinetto se è adiacente al robot
	 * @param r
	 * @param c
	 * @return Cella
	 */
	public Cella trovaRubinetto(int r, int c) {
		if (grigliaInferiore[r - 1][c] instanceof Rubinetto)
			return grigliaInferiore[r - 1][c];
		if (grigliaInferiore[r + 1][c] instanceof Rubinetto)
			return grigliaInferiore[r + 1][c];
		if (grigliaInferiore[r][c + 1] instanceof Rubinetto)
			return grigliaInferiore[r][c + 1];
		if (grigliaInferiore[r][c - 1] instanceof Rubinetto)
			return grigliaInferiore[r][c - 1];
		return null;
	}
	/**
	 * Questo metodo restituisce una lavatrice se è adiacente al robot
	 * @param r
	 * @param c
	 * @return Cella
	 */
	public Cella trovaLavatrice(int r, int c) {
		if (grigliaInferiore[r - 1][c] instanceof Lavatrice)
			return grigliaInferiore[r - 1][c];
		if (grigliaInferiore[r + 1][c] instanceof Lavatrice)
			return grigliaInferiore[r + 1][c];
		if (grigliaInferiore[r][c + 1] instanceof Lavatrice)
			return grigliaInferiore[r][c + 1];
		if (grigliaInferiore[r][c - 1] instanceof Lavatrice)
			return grigliaInferiore[r][c - 1];
		return null;
	}
	/**
	 * Questo metodo restituisce lo stato della cella: bagnato o asciutto
	 * @param r
	 * @param c
	 * @return boolean
	 */
	public boolean cercaBaganta(int r, int c) {
		if (grigliaInferiore[r][c] instanceof Vuota && grigliaInferiore[r][c].getPassato() == true) {
			return ((Vuota) grigliaInferiore[r][c]).getStato();
		}
		return false;
	}
	/**
	 * Questo metodo spegne il fornello
	 * @param r
	 * @param c
	 */
	public void spegniFornello(int r, int c) {
		((Fornello) grigliaInferiore[r][c]).setSpegni();
	}
	/**
	 * Questo metodo accende il fornello
	 * @param r
	 * @param c
	 */
	public void accendiFornello(int r, int c) {
		((Fornello) grigliaInferiore[r][c]).setAccendi();
	}
	/**
	 * Questo metodo chiude il rubinetto
	 * @param r
	 * @param c
	 */
	public void chiudiRubinetto(int r, int c) {
		((Rubinetto) grigliaInferiore[r][c]).setChiuso();
	}
	/**
	 * Questo metodo aggiusta la lavatrice
	 * @param r
	 * @param c
	 */
	public void chiudiLavatrice(int r, int c) {
		((Lavatrice) grigliaInferiore[r][c]).setChiuso();
	}
	/**
	 * Questo metodo ritorna il valore della riga su cui è posizionato il robot
	 * @return int
	 */
	public int getR() {
		return gino.getR();
	}
	/**
	 * Questo metodo ritorna il valore della colonna su cui è posizionato il robot
	 * @return int 
	 */
	public int getC() {
		return gino.getC();
	}
	/**
	 * Questo metodo genera un numero casuale da 0 a N-1
	 * @param N
	 * @return int
	 */
	 
	public static int randInt(int N) {
		Random rand = new Random();
		int randomNumber = rand.nextInt(N);
		return randomNumber;
	}

}
