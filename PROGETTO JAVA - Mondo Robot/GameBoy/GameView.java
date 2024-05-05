package GameBoy;

/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * In questa classe mi occupo dell'iterazione con l'utente presentando una
 * grafica personalizzata per il gioco
 */

public class GameView extends JPanel {

	/**
	 * Questa griglia di dimensione potenzialmente N ci permette la visualizzazione
	 * del gioco e il caricamento delle immagini negli opportuni JButton
	 */
	private JButton[][] grigliaSuperiore;
	private int dimensione;

	/**
	 * Ho realizzato le immagini per avere a disposizione tutte le icone precaricate
	 * in memoria, rendendo il gioco molto più fluido
	 */
	public URL acqua;
	public URL animale;
	public URL fornelloAcceso;
	public URL fornelloSpento;
	public URL lavatrice;
	public URL lavatriceGuasta;
	public URL mattoni;
	public URL robot;
	public URL rubinettoAperto;
	public URL rubinettoChiuso;

	/**
	 * Il costruttore del motodo prende in input la dimensione della matrice N*N
	 * inizializzando la griglia superiore All'interno richiamo sia il metodo che
	 * permette il settaggio della matrice a livello grafico e il metodo che carica
	 * le immagini con l' opportuna eccezzione
	 */

	/**
	 * Costruttore
	 * 
	 * @param dim
	 */
	public GameView(int dim) {

		this.dimensione = dim;
		grigliaSuperiore = new JButton[dimensione][dimensione];

		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new GridLayout(dimensione, dimensione));

		creaGriglia();

		try {
			caricaImmagini();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Restituisce la dimensione N della matrice
	 * 
	 * @return int
	 */
	public int getDim() {
		return dimensione;
	}

	/**
	 * Restittuisce la matrice di JButton
	 * 
	 * @return JButton[][]
	 */
	public JButton[][] getGriglia() {
		return grigliaSuperiore;
	}

	/**
	 * Inizializza griglia superiore
	 */
	public void creaGriglia() {
		for (int i = 0; i < dimensione; i++) {
			for (int j = 0; j < dimensione; j++) {
				this.grigliaSuperiore[i][j] = new JButton();
				this.grigliaSuperiore[i][j].setBackground(Color.WHITE);
				this.add(this.grigliaSuperiore[i][j]);
			}
		}
	}

	/**
	 * metodo creato non so se è utilizzato e non credo sia una cosa da fare
	 * 
	 * @return JButton[][]
	 */
	public JButton[][] getGrigliaSuperiore() {
		return this.grigliaSuperiore;
	}

	/**
	 * metodo per il caricamento delle immagini con relativa eccezione
	 * 
	 * @throws IOException
	 */
	public void caricaImmagini() throws IOException {
		acqua = getClass().getResource("/Img/ACQUA.jpg");
		animale = getClass().getResource("/Img/CANE.jpg");
		fornelloAcceso = getClass().getResource("/Img/FORNELLO ACCESO.jpg");
		fornelloSpento = getClass().getResource("/Img/FORNELLO SPENTO.jpg");
		lavatrice = getClass().getResource("/Img/LAVATRICE.jpg");
		lavatriceGuasta = getClass().getResource("/Img/LAVATRICE GUASTA.jpg");
		mattoni = getClass().getResource("/Img/MATTONI.jpg");
		robot = getClass().getResource("/Img/ROBOT.jpg");
		rubinettoChiuso = getClass().getResource("/Img/RUBINETTO.jpg");
		rubinettoAperto = getClass().getResource("/Img/RUBINETTO CHE PERDE.jpg");
	}

	/**
	 * Questo metodo setta il perimetro MURO della mappa
	 * 
	 * @param N
	 */
	public void inizializzaMuri(int N) {
		ImageIcon muro = new ImageIcon(mattoni);
		for (int i = 0; i < N; i++) {
			grigliaSuperiore[0][i].setIcon(muro);
			grigliaSuperiore[N - 1][i].setIcon(muro);

			for (int j = 0; j < N; j++) {
				grigliaSuperiore[j][0].setIcon(muro);
				grigliaSuperiore[j][N - 1].setIcon(muro);
			}
		}
	}

	/**
	 * Metodo che inizializza l'icona del robot all'interno della mappa
	 * 
	 * @param r
	 * @param c
	 */
	protected void inizializzaRobot(int r, int c) {
		this.grigliaSuperiore[r][c].setIcon(new ImageIcon(robot));
		settaTrueTutteLeCelleAdiacentiARobot(r, c);
	}

	/**
	 * Metodo che inizializza l'icona dell'animale domestico all'interno della mappa
	 * 
	 * @param r
	 * @param c
	 */
	protected void inizializzaAnimaleDomestico(int r, int c) {
		this.grigliaSuperiore[r][c].setIcon(new ImageIcon(animale));
	}

	/**
	 * Metodo che ci permette di aggiornare la posizione del robot, passando come
	 * parametri le coordinate nuove e quelle vecchie
	 * 
	 * @param rN
	 * @param cN
	 * @param rV
	 * @param cV
	 */
	protected void muoviRobot(int rN, int cN, int rV, int cV) {
		grigliaSuperiore[rV][cV].setIcon(null);
		grigliaSuperiore[rN][cN].setIcon(new ImageIcon(robot));
		settaTrueTutteLeCelleAdiacentiARobot(rN, cN);
	}

	/**
	 * Metodo che ci permette di aggiornare la posizione dell' animaleDomestico,
	 * passando come parametri le coordinate nuove, quelle vecchie e se la cella è
	 * stata attraversata dal robot o meno
	 * 
	 * @param rN
	 * @param cN
	 * @param rV
	 * @param cV
	 * @param passato
	 */
	protected void muoviAnimaleDomestico(int rN, int cN, int rV, int cV, boolean passato) {
		if (passato == true) {
			grigliaSuperiore[rV][cV].setIcon(null);
			grigliaSuperiore[rN][cN].setIcon(new ImageIcon(animale));
		}
		if (passato == false) {
			grigliaSuperiore[rV][cV].setIcon(null);
		}
	}

	/**
	 * Metodo che permette di visualizzare le celle già vista dal robot
	 * 
	 * @param rr
	 * @param cc
	 */
	protected void settaTrueTutteLeCelleAdiacentiARobot(int rr, int cc) {
		grigliaSuperiore[rr][cc].setBackground(Color.CYAN);
		grigliaSuperiore[rr - 1][cc].setBackground(Color.CYAN);
		grigliaSuperiore[rr + 1][cc].setBackground(Color.CYAN);
		grigliaSuperiore[rr][cc + 1].setBackground(Color.CYAN);
		grigliaSuperiore[rr][cc - 1].setBackground(Color.CYAN);
	}

	/**
	 * Metodo che setta l'icona fornello tenendo conto dello stato
	 * 
	 * @param r
	 * @param c
	 * @param stato
	 */
	protected void setIconFornello(int r, int c, boolean stato) {
		if (stato == true)
			grigliaSuperiore[r][c].setIcon(new ImageIcon(fornelloAcceso));
		if (stato == false)
			grigliaSuperiore[r][c].setIcon(new ImageIcon(fornelloSpento));
	}

	/**
	 * Metodo che setta l'icona rubinetto tenendo conto dello stato
	 * 
	 * @param r
	 * @param c
	 * @param stato
	 */
	protected void setIconRubinetto(int r, int c, boolean stato) {
		if (stato == true)
			grigliaSuperiore[r][c].setIcon(new ImageIcon(rubinettoAperto));
		if (stato == false)
			grigliaSuperiore[r][c].setIcon(new ImageIcon(rubinettoChiuso));

	}

	/**
	 * Metodo che setta l'icona lavatrice tenendo conto dello stato
	 * 
	 * @param r
	 * @param c
	 * @param stato
	 */
	protected void setIconLavatrice(int r, int c, boolean stato) {
		if (stato == true)
			grigliaSuperiore[r][c].setIcon(new ImageIcon(lavatriceGuasta));
		if (stato == false)
			grigliaSuperiore[r][c].setIcon(new ImageIcon(lavatrice));
	}

	/**
	 * Metodo che setta l'icona bagnata
	 * 
	 * @param r
	 * @param c
	 */
	public void setIconBaganata(int r, int c) {
		if (grigliaSuperiore[r][c].getIcon() == null) {
			grigliaSuperiore[r][c].setIcon(new ImageIcon(acqua));
		}
	}

	/**
	 * Metodo che setta l'icona dell'animale domestico
	 * 
	 * @param r
	 * @param c
	 */
	protected void setIconAnimaleDomestico(int r, int c) {
		grigliaSuperiore[r][c].setIcon(new ImageIcon(animale));
	}

	/**
	 * Metodo che realizza un popup temporizzato per la visualizzazione dell'errori
	 * 
	 * @param str
	 * @return str
	 */
	public String errore(String str) {
		Timer timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.getRootFrame().dispose(); // Chiude la finestra di dialogo dopo 3 secondi
			}
		});

		timer.setRepeats(false); // Imposta il timer in modo che venga eseguito solo una volta
		timer.start();

		JOptionPane.showMessageDialog(null, str, "Avviso", JOptionPane.WARNING_MESSAGE);
		return str;
	}

	/**
	 * Metodo che realizza un popup per chiedere all'utente di inserire la
	 * dimensione della mappa: lancia un'eccezione di tipo NumberFormatException se
	 * l'argomento è nullo oppure un'eccezione di tipo IllegalArgumentException se
	 * l'argomento è minore o uguale a 10
	 * @return int
	 */
	public static int input() {
		String output = JOptionPane.showInputDialog("Inserire dimensione mappa");
		int dim = 0;
		try {
			dim = Integer.parseInt(output);

			if (output == null || dim <= 10) {
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Inserire un numero valido per la dimensione della mappa", "Errore",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Inserire una dimensione maggiore di 10 per avviare il gioco", "Avviso",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		return dim;
	}
}
