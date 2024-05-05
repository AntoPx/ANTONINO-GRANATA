package Componenti;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
public class AnimaleDomestico{
	
	private int r;
	private int c;
	/**
	 * Costruttore
	 * @param r
	 * @param c
	 */
	public AnimaleDomestico(int r, int c) {
		this.r = r;
		this.c = c;
	}
	/**
	 * Ritorna il valore della riga
	 * @return int
	 */
	public int getR() {
		return r;
	}
    /** 
     * Setta il valore della riga
     * @param r
     */
	public void setR(int r) {
		this.r = r;
	}
	/**
	 * Ritorna il valore della colonna
	 * @return int
	 */
	public int getC() {
		return c;
	}
	/**
	 * Setta il valore della colonna
	 * @param c
	 */
	public void setC(int c) {
		this.c = c;
	}
	
}
