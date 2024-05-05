package Componenti;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
public class Vuota extends Cella{
	
	private boolean bagnata;
	/**
	 * Costruttore
	 * @param r
	 * @param c
	 * @param passato
	 */
	public Vuota(int r, int c, boolean passato) {
		super(r, c, passato);
	}
	/**
	 * Setta la cella vuota come asciutta
	 */
	public void setAsciutta() {
		this.bagnata = false;
	}
	/**
	 * Setta la cella vuota come bagnata
	 */
	public void setBagnata() {
		this.bagnata = true;
	}
	/**
	 * Ritorna lo stato della cella vuota
	 * @return boolean
	 */
	public boolean getStato() {
		return this.bagnata;
	}
	
}
