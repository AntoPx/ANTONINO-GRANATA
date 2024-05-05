package Componenti;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
public class Lavatrice extends Cella{

	private boolean stato;
	/**
	 * Costruttore
	 * @param r
	 * @param c
	 * @param passato
	 * @param aperto
	 */
	public Lavatrice(int r, int c, boolean passato, boolean stato) {
		super(r, c, passato);
		this.stato = stato;
	}
	/**
	 * Setta la lavatrice guasta
	 */
	public void setAperto() {
		this.stato = true;
	}
	/**
	 * Setta la lavatrice riparata
	 */
	public void setChiuso() {
		this.stato = false;
	}
	/**
	 * Ritorna lo stato della lavatrice
	 * @return boolean
	 */
	public boolean getStato() {
		return stato;
	}
}
