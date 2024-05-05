package Componenti;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
public abstract class Cella{

	private int r;
	private int c;
	private boolean passato;
	/**
	 * Costruttore
	 * @param r
	 * @param c
	 * @param passato
	 */
	public Cella(int r, int c, boolean passato) {
		super();
		this.r = r;
		this.c = c;
		this.passato = passato;
	}
	/**
	 * Ritorna il valore della riga
	 * @return int
	 */
	public int getR() {
		return r;
	}
	/**
	 * Ritorna il valore della colonna
	 * @return int
	 */
	public int getC() {
		return c;
	}
	/**
	 * Setta le coordinate riga-colonna
	 * @param r
	 * @param c
	 */
	public void setCoordinate(int r, int c) {
		this.r = r;
		this.c = c;
	}
	/**
	 * Setta che la cella è stata visitata
	 */
	public void setPassato() {
		this.passato = true;
	}
	/**
	 * Ritorna lo stato di passato
	 * @return boolean
	 */
	public boolean getPassato() {
		return this.passato;
	}
}
