package Componenti;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
public class Fornello extends Cella {

	private boolean acceso;
	/**
	 * Costruttore
	 * @param r
	 * @param c
	 * @param passato
	 * @param acceso
	 */
	public Fornello(int r, int c,boolean passato, boolean acceso) {
		super(r, c, passato);
		this.acceso = acceso;
	}
	/**
	 * Setta il fornello acceso
	 */
	public void setAccendi() {
		this.acceso = true;
	}
	/**
	 * Setta il fornello spento
	 */
	public void setSpegni() {
		this.acceso = false;
	}
	/**
	 * Ritorna lo stato del fornello
	 * @return boolean
	 */
	public boolean getStato() {
		return acceso;
	}

}
