package GameBoy;
/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import Componenti.Fornello;
import Componenti.Lavatrice;
import Componenti.Rubinetto;
import Componenti.Vuota;

//riceve i comandi dall'utente tramite il view
public class Controller extends JFrame {

	public GameModel model;
	private GameView view;
	private SceltaView viewS;
	
	private GameView viewChiaro;

	private avviaThPerditaAcqua acquaRubinetto;
	private avviaThPerditaAcqua acquaLavatrice;

	private ActionListener listener;
	
	/**
	 * Costruttore
	 * @param modelG
	 * @param viewG
	 * @param viewS
	 * @param viewChiaro
	 */
	public Controller(GameModel modelG, GameView viewG, SceltaView viewS, GameView viewChiaro) {

		this.model = modelG;
		this.view = viewG;
		this.viewS = viewS;
		this.viewChiaro = viewChiaro;
		
		creaGriglia();

		selezionaScelta();

		// inizializzo l'intera mappa
		inizializzaMuri();

		inizializzaRobot();
		inizializzaAnimaleDomestico();

		inizializzaFornello();
		inizializzaRubinetto();
		inizializzaLavatrice();

		avviaThAnimaleDomestico();

		this.setLayout(new BorderLayout());
		this.setSize(1500, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(this.view, BorderLayout.WEST);
		this.add(this.viewS, BorderLayout.CENTER);
		this.add(this.viewChiaro, BorderLayout.EAST);
		this.setVisible(true);
	}
	/**
	 * Questo metodo aggiunge le actionListener ad ogni bottone
	 */
	private void creaGriglia() {

		listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = 0;
				int y = 0;

				JButton b = (JButton) e.getSource();
				int i = 0;
				while (i < model.getDimensione()) {
					int j = 0;
					while (j < model.getDimensione()) {
						if (view.getGrigliaSuperiore()[i][j] == b) {
							x = i;
							y = j;
						}
						j++;
					}
					i++;
				}
				muoviRobot(x, y);
			}
		};

		// Cicli for per inserire all'interno della grigliaSuperiore l'action listener
		// ingrado di inviare i comandi

		for (int i = 0; i < model.getDimensione(); i++) {
			for (int j = 0; j < model.getDimensione(); j++) {
				view.getGrigliaSuperiore()[i][j].addActionListener(listener);
				view.add(view.getGrigliaSuperiore()[i][j]);
			}
		}
	}
	/**
	 * Questo metodo inserisce un actionListener per ogni bottone di scelta
	 */
	private void selezionaScelta() {
		for (int i = 0; i < 5; i++) {
			viewS.buttons[i].addActionListener(e -> {
				JButton button = (JButton) e.getSource();
				if (button.getText().equals("Asciuga")) {
				    if (!((Vuota) model.grigliaInferiore[model.gino.getR()][model.gino.getC()]).getStato()) {
				        throw new IllegalStateException(view.errore("Cella asciutta!"));
				    }
				    setAsciutta(model.gino.getR(), model.gino.getC());
				}
			    if (button.getText().equals("Spegni fornello")) {
				    Fornello fornello = ((Fornello)model.trovaFornello(model.gino.getR(), model.gino.getC()));
				    if (fornello == null || !fornello.getStato()) {
				        throw new IllegalStateException(view.errore("Nessun fornello acceso da spegnere!"));
				    }
				    spegniFornello(model.gino.getR(), model.gino.getC());
				}
				if (button.getText().equals("Accendi fornello")) {
				    Fornello fornello = ((Fornello)model.trovaFornello(model.gino.getR(), model.gino.getC()));
				    if (fornello == null || fornello.getStato()) {
				        throw new IllegalStateException(view.errore("Nessun fornello spento da accendere!"));
				    }
				    accendiFornello(model.gino.getR(), model.gino.getC());
				}
				if (button.getText().equals("Chiudi rubinetto")) {
				    Rubinetto rubinetto = ((Rubinetto)model.trovaRubinetto(model.gino.getR(), model.gino.getC()));
				    if (rubinetto == null || !rubinetto.getStato()) {
				        throw new IllegalStateException(view.errore("Nessun rubinetto aperto da chiudere!"));
				    }
				    chiudiRubinetto(model.gino.getR(), model.gino.getC());
				}
				if (button.getText().equals("Aggiusta lavatrice")) {
				    Lavatrice lavatrice = ((Lavatrice)model.trovaLavatrice(model.gino.getR(), model.gino.getC()));
				    if (lavatrice == null || !lavatrice.getStato()) {
				    	throw new IllegalStateException(view.errore("Nessuna lavatrice da aggiustare!"));
				    }
				    chiudiLavatrice(model.gino.getR(), model.gino.getC());
				}
			});
		}
	}
	/**
	 * Questo metodo inizializza il perimetro MURO della mappa di gioco e della mappa di controllo 
	 */
	private void inizializzaMuri() {
		model.inizializzaMuri(model.getDimensione());
		view.inizializzaMuri(model.getDimensione());
		viewChiaro.inizializzaMuri(model.getDimensione());
	}
	/**
	 * Questo metodo inizializza il fornello in maniera casuale
	 */
	private void inizializzaFornello() {
		int r = setRandomInt(model.getDimensione());
		int c = setRandomInt(model.getDimensione());
		boolean stato = setRandomBoolean();
		model.inizializzaFornello(r, c, model.grigliaInferiore[r][c].getPassato(), stato);
		viewChiaro.setIconFornello(r, c, stato);
		if (model.grigliaInferiore[r][c].getPassato() == true) {
			view.setIconFornello(r, c, stato);
		}
	}
	/**
	 * Questo metodo inizializza il rubinetto in maniera casuale
	 */
	private void inizializzaRubinetto() {
		int r = setRandomInt(model.getDimensione());
		int c = setRandomInt(model.getDimensione());
		boolean stato = setRandomBoolean();
		model.inizializzaRubinetto(r, c, model.grigliaInferiore[r][c].getPassato(), stato);
		viewChiaro.setIconRubinetto(r, c, stato);
		if (model.grigliaInferiore[r][c].getPassato() == true) {
			view.setIconRubinetto(r, c, stato);
		}
		if (stato == true) {
			acquaRubinetto = new avviaThPerditaAcqua(r, c, 10);
		}
	}
	/**
	 * Questo metodo inizializza la lavatrice in maniera casuale
	 */
	private void inizializzaLavatrice() {
		int r = setRandomInt(model.getDimensione());
		int c = setRandomInt(model.getDimensione());
		boolean stato = setRandomBoolean();
		model.inizializzaLavatrice(r, c, model.grigliaInferiore[r][c].getPassato(), stato);
		viewChiaro.setIconLavatrice(r, c, stato);
		if (model.grigliaInferiore[r][c].getPassato() == true) {
			view.setIconLavatrice(r, c, stato);
		}
		if (stato == true) {
			acquaLavatrice = new avviaThPerditaAcqua(r, c, 15);
		}
	}
	/**
	 * Questo metodo inizializza l'animaleDomestico in maniera casuale
	 */
	private void inizializzaAnimaleDomestico() {
		int r = setRandomInt(model.getDimensione());
		int c = setRandomInt(model.getDimensione());
		model.inizializzaAnimaleDomestico(r, c);
		viewChiaro.inizializzaAnimaleDomestico(r, c);
		if (model.grigliaInferiore[r][c].getPassato() == true) {
			view.inizializzaAnimaleDomestico(r, c);
		}
	}

	/**
	 * Questo metodo inizializza il robot in maniera casuale
	 */
	private void inizializzaRobot() {
		int r = setRandomInt(model.getDimensione());
		int c = setRandomInt(model.getDimensione());
		model.inizializzaRobot(r, c);
		view.inizializzaRobot(r, c);
		viewChiaro.inizializzaRobot(r, c);
	}
	
	/**
	 * Questo metodo permette di muovere il robot all'interno della mappa
	 * @param x
	 * @param y
	 */
	public void muoviRobot(int x, int y) {
		int r = model.getR();
		int c = model.getC();
		if (model.muoviRobot(x, y) == true) {
			view.muoviRobot(x, y, r, c);
			trovaFornello(x, y);
			trovaRubinetto(x, y);
			trovaLavatrice(x, y);
			trovaBagnata(x, y);
		} else
			view.errore("Non posso eseguire questa mossa");
	}

	/**
	 * Questo metodo trova il fornello se adiacente al robot
	 */
	private void trovaFornello(int r, int c) {
		if (model.trovaFornello(r, c) instanceof Fornello && model.trovaFornello(r, c) != null) {
			view.setIconFornello(model.trovaFornello(r, c).getR(), model.trovaFornello(r, c).getC(),
					((Fornello) model.trovaFornello(r, c)).getStato());
		}
	}
	/**
	 * Questo metodo trova il rubinetto se adiacente al robot
	 */
	private void trovaRubinetto(int r, int c) {
		if (model.trovaRubinetto(r, c) instanceof Rubinetto && model.trovaRubinetto(r, c) != null) {
			view.setIconRubinetto(model.trovaRubinetto(r, c).getR(), model.trovaRubinetto(r, c).getC(),
					((Rubinetto) model.trovaRubinetto(r, c)).getStato());
		}
	}
	/**
	 * Questo metodo trova la lavatrice se adiacente al robot
	 */
	private void trovaLavatrice(int r, int c) {
		if (model.trovaLavatrice(r, c) instanceof Lavatrice && model.trovaLavatrice(r, c) != null) {
			view.setIconLavatrice(model.trovaLavatrice(r, c).getR(), model.trovaLavatrice(r, c).getC(),
					((Lavatrice) model.trovaLavatrice(r, c)).getStato());
		}
	}
	
	/**
	 * Questo metodo trova la cella bagnata e la setta come tale
	 * @param r
	 * @param c
	 */
	private void trovaBagnata(int r, int c) {
		if (model.cercaBaganta(r - 1, c) == true) {
			view.setIconBaganata(r - 1, c);
		}
		if (model.cercaBaganta(r + 1, c) == true) {
			view.setIconBaganata(r + 1, c);
		}
		if (model.cercaBaganta(r, c + 1) == true) {
			view.setIconBaganata(r, c + 1);
		}
		if (model.cercaBaganta(r, c - 1) == true) {
			view.setIconBaganata(r, c - 1);
		}
	}

	/**
	 * Questo metodo setta la cella baganta
	 * @param r
	 * @param c
	 */
	public void setBagnata(int r, int c) {
		model.setBagnata(r, c);
		if (model.grigliaInferiore[r][c].getPassato() == true) {
			view.setIconBaganata(r, c);
		}
	}
	/**
	 * Questo metodo setta la cella asciutta
	 * @param r
	 * @param c
	 */
	private void setAsciutta(int r, int c) {
		model.setAsciutta(r, c);
	}

	/**
	 * Questo metodo spegne il fornello
	 * @param r
	 * @param c
	 */
	private void spegniFornello(int r, int c) {
		if (model.trovaFornello(r, c) instanceof Fornello && model.trovaFornello(r, c) != null
				&& ((Fornello) model.trovaFornello(r, c)).getStato() == true) {
			model.spegniFornello(model.trovaFornello(r, c).getR(), model.trovaFornello(r, c).getC());
			view.setIconFornello(model.trovaFornello(r, c).getR(), model.trovaFornello(r, c).getC(), false);
		} else
			view.errore("Nessun fornello da spegnere");
	}

	/**
	 * Questo metodo accende il fornello
	 * @param r
	 * @param c
	 */
	private void accendiFornello(int r, int c) {
		if (model.trovaFornello(r, c) instanceof Fornello && model.trovaFornello(r, c) != null
				&& ((Fornello) model.trovaFornello(r, c)).getStato() == false) {
			model.accendiFornello(model.trovaFornello(r, c).getR(), model.trovaFornello(r, c).getC());
			view.setIconFornello(model.trovaFornello(r, c).getR(), model.trovaFornello(r, c).getC(), true);
		} else
			view.errore("Nessun fornello da accendere");
	}

	/**
	 * Questo metodo chiude il rubinetto
	 * @param r
	 * @param c
	 */
	private void chiudiRubinetto(int r, int c) {
		if (model.trovaRubinetto(r, c) instanceof Rubinetto && model.trovaRubinetto(r, c) != null
				&& ((Rubinetto) model.trovaRubinetto(r, c)).getStato() == true) {
			model.chiudiRubinetto(model.trovaRubinetto(r, c).getR(), model.trovaRubinetto(r, c).getC());
			view.setIconRubinetto(model.trovaRubinetto(r, c).getR(), model.trovaRubinetto(r, c).getC(), false);
			acquaRubinetto.stopTh();
		} else
			view.errore("Nessun rubinetto da chiudere");
	}

	/**
	 * Questo metodo aggiusta la lavatrice
	 * @param r
	 * @param c
	 */
	private void chiudiLavatrice(int r, int c) {
		if (model.trovaLavatrice(r, c) instanceof Lavatrice && model.trovaLavatrice(r, c) != null
				&& ((Lavatrice) model.trovaLavatrice(r, c)).getStato() == true) {
			model.chiudiLavatrice(model.trovaLavatrice(r, c).getR(), model.trovaLavatrice(r, c).getC());
			view.setIconLavatrice(model.trovaLavatrice(r, c).getR(), model.trovaLavatrice(r, c).getC(), false);
			acquaLavatrice.stopTh();
		} else
			view.errore("Nessuna lavatrice da sistemare");
	}

	/**
	 * Questo metodo genera un numero casuale tra 1 e N-1
	 * @param N 
	 * @return int
	 */
	private int setRandomInt(int N) {
		Random rand = new Random();
		int randomNumber = rand.nextInt(N - 2) + 1;
		return randomNumber;
	}

	/**
	 * Questa metodo restituisce true o false in modo casuale
	 * @return
	 */
	private boolean setRandomBoolean() {
		Random rand = new Random();
		boolean randomBoolean = rand.nextBoolean();
		return randomBoolean;
	}

	/**
	 * Questo metodo avvia il thread per l'animale domenstico
	 */
	public void avviaThAnimaleDomestico() {

		Thread thAnimale = new Thread(new Runnable() {
			
			/**
			 * Questo metodo permette al cane di muoversi in maniera randomica ogni 0,5 secondi
			 */
			@Override
			public void run() {
				while (true) {
					int n = GameModel.randInt(4);
					muoviAnimeleDomestico(n);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			/**
			 * Questo metodo permette di muovere l'animale domestico
			 * @param x
			 * @param y
			 */
			public void muoviAnimeleDomestico(int x, int y) {
				int xx = model.cane.getR();
				int yy = model.cane.getC();
				if (model.muoviAnimaleDomestico(x, y) == true) {
					view.muoviAnimaleDomestico(x, y, xx, yy, model.grigliaInferiore[x][y].getPassato());
					trovaBagnata(x, y);
				} else {
					trovaBagnata(xx, yy);
				}
			}
			
			/**
			 * Questo metodo permette di scegliere la cella adiacente dove andare
			 * @param n
			 */
			private void muoviAnimeleDomestico(int n) {
				// avanti
				if (n == 0) {
					muoviAnimeleDomestico(model.cane.getR() - 1, model.cane.getC());
				}
				// dietro
				else if (n == 1) {
					muoviAnimeleDomestico(model.cane.getR() + 1, model.cane.getC());
				}
				// destra
				else if (n == 2) {
					muoviAnimeleDomestico(model.cane.getR(), model.cane.getC() + 1);
				}
				// sinista
				else if (n == 3) {
					muoviAnimeleDomestico(model.cane.getR(), model.cane.getC() - 1);
				}
			}
		});
		thAnimale.start();
	}
	
	/**
	 * 
	 * Questa classe è una classe annidata perchè è strettamente associata alla classe esterna
	 * Non è statica perchè abbiamo bisogno di implementare più istanze della stessa classe annidata all'interno della classe esterna
	 */
	
	public class avviaThPerditaAcqua {

		boolean condizione;
		int riga;
		int colonna;

		/**
		 * Costruttore
		 * @param r
		 * @param c
		 * @param time
		 */
		public avviaThPerditaAcqua(int r, int c, int time) {
			this.condizione = true;
			this.riga = r;
			this.colonna = c;
			avviaTh(r, c, time);
		}

		/**
		 * Questo metodo permette di avviare il thread con un tempo di attesa stabilito
		 * @param r
		 * @param c
		 * @param time
		 * @return Thread
		 */
		public Thread avviaTh(int r, int c, int time) {
			
			riga = r;
			colonna = c;
			
			Thread thPerditaAcqua = new Thread(new Runnable() {
				
				/**
				 * Questo metodo ci permette di eseguire il thread e di terminarne l'esecuzione, modificando la variabile condizione
				 */
				@Override
				public void run() {
					while (condizione) {
						allagaCella(GameModel.randInt(4), riga, colonna);
						try {
							Thread.sleep(time*1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				/**
				 * Questo metodo bagna la cella
				 * @param r
				 * @param c
				 */
				private void perditaAdiacente(int r, int c) {
					if (model.grigliaInferiore[r][c] instanceof Vuota) {
						riga = r;
						colonna = c;
						setBagnata(riga, colonna);
					}
				}

				
				/**
				 * Questo metodo seleziona la cella da bagnare
				 * @param n
				 * @param r
				 * @param c
				 */
				private void allagaCella(int n, int r, int c) {
					// avanti
					if (n == 0) {
						perditaAdiacente(r - 1, c);
					}
					// destro
					else if (n == 1) {
						perditaAdiacente(r + 1, c);
					}
					// destra
					else if (n == 2) {
						perditaAdiacente(r, c + 1);
					}
					// sinista
					else if (n == 3) {
						perditaAdiacente(r, c - 1);
					}
				}

			});
			thPerditaAcqua.start();
			return thPerditaAcqua;
		}
		
		/**
		 * Questo metodo stoppa il thread
		 */
		public void stopTh() {
			condizione = false;
		}
		
		/**
		 * Questo metodo ritorna il valore della riga
		 * @return int
		 */
		public int getR() {
			return riga;
		}
		
		/**
		 * Questo metodo ritona il valore della colonna
		 * @return int
		 */
		public int getC() {
			return colonna;
		}
	}

}
