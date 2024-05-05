package Componenti;

import javax.swing.ImageIcon;

import GameBoy.Cella;

public class RubLav extends Cella {

	private ImageIcon immagine;
	
	private boolean aperto;

	public RubLav(String immagine, int r, int c, boolean stato) {
		super(r, c);
		this.immagine = new ImageIcon(immagine);
		this.aperto = stato;
	}

	public ImageIcon getImmagine() {
		return immagine;
	}
	
	public void setAperto() {
		this.aperto = true;
	}
	
	public void setChiuso() {
		this.aperto = false;
	}
	
	public boolean getStato() {
		return aperto;
	}
}