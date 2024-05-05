package Componenti;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
public class Rubinetto extends Cella{

	private boolean aperto;
	/**
	 * Costruttore
	 * @param r
	 * @param c
	 * @param passato
	 * @param stato
	 */
	public Rubinetto(int r, int c, boolean passato, boolean stato) {
		super(r, c, passato);
		this.aperto = stato;
	}
	/**
	 * Setta lo stato come apeto
	 */
	public void setAperto() {
		this.aperto = true;
	}
	/**
	 * Setta lo stato come chiuso
	 */
	public void setChiuso() {
		this.aperto = false;
	}
	/**
	 * Ritorna lo stato del rubinetto
	 * @return boolean
	 */
	public boolean getStato() {
		return aperto;
	}
}