package GameBoy;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SceltaView extends JPanel {

	JButton[] buttons;

	/**
	 * Costruttore
	 */
	public SceltaView() {
		this.setPreferredSize(new Dimension(0, 310));
	    this.setLayout(new GridLayout(5,0));
	    this.buttons = new JButton[5];
	    settaScelte();
	}
	/**
	 * Setta le mosse che l'utente può eseguire durante il gioco
	 */
	public void settaScelte() {
		buttons[0] = new JButton();
		buttons[0].setText("Asciuga");
		buttons[0].setBackground(Color.WHITE);
		add(buttons[0]);
		buttons[1] = new JButton();
		buttons[1].setText("Spegni fornello");
		buttons[1].setBackground(Color.WHITE);
		add(buttons[1]);
		buttons[2] = new JButton();
		buttons[2].setText("Accendi fornello");
		buttons[2].setBackground(Color.WHITE);
		add(buttons[2]);
		buttons[3] = new JButton();
		buttons[3].setText("Chiudi rubinetto");
		buttons[3].setBackground(Color.WHITE);
		add(buttons[3]);
		buttons[4] = new JButton();
		buttons[4].setText("Aggiusta lavatrice");
		buttons[4].setBackground(Color.WHITE);
		add(buttons[4]);
	}
	
}
