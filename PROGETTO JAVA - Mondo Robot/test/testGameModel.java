package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Componenti.AnimaleDomestico;
import Componenti.Fornello;
import Componenti.Lavatrice;
import Componenti.Muro;
import Componenti.Robot;
import Componenti.Rubinetto;
import Componenti.Vuota;
import GameBoy.GameModel;

class testGameModel {
	
	private static int DIMENSIONE = 12;
	private GameModel game;
	
	@BeforeEach
	void testInizializzaGameModel() {
		game = new GameModel(DIMENSIONE);
		game.inizializzaRobot(2,2);
		game.inizializzaAnimaleDomestico(3,3);
	}

	@Test
	void testCreaGrilia() {
		for (int i = 0; i < game.getDimensione(); i++) {
			for (int j = 0; j < game.getDimensione(); j++) {
				assertEquals(game.grigliaInferiore[i][j].getClass(), new Vuota(i, j, false).getClass());
				assertTrue(game.grigliaInferiore[i][j] instanceof Vuota);
			}
		}
	}
	
	
	
	@Test
	void testInizializzaRubinetto() {
		int r = 8;
		int c = 3;
		game.inizializzaRubinetto(r, c, false, true);
		assertTrue(game.getGrigliaInferiore()[r][c] instanceof Rubinetto);
		assertEquals(false, ((Rubinetto) game.getGrigliaInferiore()[r][c]).getPassato());
		assertEquals(true, ((Rubinetto) game.getGrigliaInferiore()[r][c]).getStato());
		
	
	}

	@Test
	public void testInizializzaLavatrice() {
		int r = 4;
		int c = 1;
		game.inizializzaLavatrice(r, c, false, false);
		assertTrue(game.getGrigliaInferiore()[r][c] instanceof Lavatrice);
		assertEquals(false, ((Lavatrice) game.getGrigliaInferiore()[r][c]).getPassato());
		assertEquals(false, ((Lavatrice) game.getGrigliaInferiore()[r][c]).getStato());
	}

	@Test
	public void testInizializzaFornello() {
		int r = 7;
		int c = 2;
		game.inizializzaFornello(r, c, false, true);
		assertTrue(game.getGrigliaInferiore()[r][c] instanceof Fornello);
		assertEquals(false, ((Fornello) game.getGrigliaInferiore()[r][c]).getPassato());
		assertEquals(true, ((Fornello) game.getGrigliaInferiore()[r][c]).getStato());
	}

	@Test
	public void testInizializzaMuri() {
		int N = game.getDimensione();

		game.inizializzaMuri(N);

		// Verifica che tutte le celle di muro siano correttamente inizializzate
		for (int i = 0; i < N; i++) {
			assertTrue(game.getGrigliaInferiore()[0][i] instanceof Muro);
			assertTrue(game.getGrigliaInferiore()[N - 1][i] instanceof Muro);
		}
		for (int j = 0; j < N; j++) {
			assertTrue(game.getGrigliaInferiore()[j][0] instanceof Muro);
			assertTrue(game.getGrigliaInferiore()[j][N - 1] instanceof Muro);
		}
	}

	@Test
	public void testInizializzaRobot() {
		
		// Verifica che il robot sia stato inizializzato correttamente nella griglia
		assertTrue(game.gino instanceof Robot);
		assertNotNull(game.gino);

	}

	@Test
	public void testInizializzaAnimaleDomestico() {

		// Verifica che l'animale domestico sia stato inizializzato correttamente nella
		// griglia
		assertTrue(game.cane instanceof AnimaleDomestico);
		assertNotNull(game.cane);

	}

	@Test
	public void testMuoviRobot_Movivemtni() {


	    // Movimento del Robot in una posizione adiacente valida (2, 3)
	    boolean result = game.muoviRobot(2, 3);
	    assertTrue(result);
	    assertEquals(2, game.getR());
	    assertEquals(3, game.getC());

	    // Movimento del Robot in una posizione non adiacente valida (1, 3)
	    result = game.muoviRobot(1, 3);
	    assertTrue(result);
	    assertEquals(1, game.getR());
	    assertEquals(3, game.getC());

	    // Movimento del Robot in una posizione non adiacente non valida (4, 4)
	    result = game.muoviRobot(4, 4);
	    assertFalse(result);
	    assertEquals(1, game.getR()); // La posizione del Robot non � cambiata
	    assertEquals(3, game.getC());
		
		
	}

	
	@Test
	public void testMuoviAnimaleDomestico_Movimenti() {
		
	    // Movimento dell'AnimaleDomestico in una posizione adiacente valida (3, 4)
	    boolean result = game.muoviAnimaleDomestico(3, 4);
	    assertTrue(result);
	    assertEquals(3, game.getGrigliaInferiore()[3][4].getR());
	    assertEquals(4, game.getGrigliaInferiore()[3][4].getC());

	    // Movimento dell'AnimaleDomestico in una posizione non adiacente valida (2, 4)
	    result = game.muoviAnimaleDomestico(2, 4);
	    assertTrue(result);
	    assertEquals(2, game.getGrigliaInferiore()[2][4].getR());
	    assertEquals(4, game.getGrigliaInferiore()[2][4].getC());

	    // Movimento dell'AnimaleDomestico in una posizione non adiacente non valida (1, 1)
	    result = game.muoviAnimaleDomestico(1, 1);
	    assertFalse(result);
	    assertEquals(2, game.getGrigliaInferiore()[2][4].getR()); // La posizione dell'AnimaleDomestico non � cambiata
	    assertEquals(4, game.getGrigliaInferiore()[2][4].getC());
	}

	
	@Test
	public void testCelleAdiacentiRobot() {
		game.settaTrueTutteLeCelleAdiacentiARobot();
		
		int r = game.gino.getR();
		int c = game.gino.getC();
		assertTrue(game.grigliaInferiore[r-1][c].getPassato());
		assertTrue(game.grigliaInferiore[r+1][c].getPassato());
		assertTrue(game.grigliaInferiore[r][c-1].getPassato());
		assertTrue(game.grigliaInferiore[r][c+1].getPassato());
		
	}
	
	
	       
	 @Test
	  void testTrovaFornello() {
	    // Posizionamento di un Fornello iniziale in posizione (2, 3)
	    game.inizializzaFornello(2,3, true, false);
	    assertNotNull(game.trovaFornello(2,2));
	    assertTrue(game.trovaFornello(2,2) instanceof Fornello);
	    assertFalse(game.trovaFornello(2,1) instanceof Fornello);
	    
	  }
	 
	 @Test
	  void testTrovaLavatrice() {
	    // Posizionamento di un Lavatrice iniziale in posizione (2, 3)
	    game.inizializzaLavatrice(1,2, true, false);
	    assertNotNull(game.trovaLavatrice(2,2));
	    assertTrue(game.trovaLavatrice(2,2) instanceof Lavatrice);
	    assertFalse(game.trovaLavatrice(4,1) instanceof Lavatrice);
	    
	  }
	 
	 @Test
	  void testTrovaRubinetto() {
	    // Posizionamento di un Rubinetto iniziale in posizione (3, 2)
	    game.inizializzaRubinetto(3,2, true, false);
	    assertNotNull(game.trovaRubinetto(2,2));
	    assertTrue(game.trovaRubinetto(2,2) instanceof Rubinetto);
	    assertFalse(game.trovaRubinetto(3,5) instanceof Rubinetto);
	    
	  }
	       
	 @Test
	  void testCercaBaganta() {
	    // Posizionamento di una cella Vuota iniziale in posizione (5,5) con stato bagnata
	    game.getGrigliaInferiore()[5][5] = new Vuota(5,5, true);
	    game.setBagnata(5,5);
	    // Ricerca di una cella Vuota bagnata nella posizione (5, 5)
	    boolean result = game.cercaBaganta(5, 5);
	  
	    // Verifica che la cella Vuota bagnata sia stata trovata nella posizione (2, 2)
	    assertTrue(result);
	  }
	 
	 @Test
	  void testSetBagnata() {
	    // Posizionamento di una cella Vuota iniziale in posizione (3, 3)
	    game.getGrigliaInferiore()[3][3] = new Vuota(3, 3, false);

	    // Impostazione della cella come bagnata
	    game.setBagnata(3, 3);

	    // Verifica che la cella sia stata impostata correttamente come bagnata
	    Vuota cellaVuota = (Vuota) game.getGrigliaInferiore()[3][3];
	    assertTrue(cellaVuota.getStato());
	  }

	  @Test
	  void testSetAsciutta() {
	    // Posizionamento di una cella Vuota iniziale in posizione (6, 6)
	    game.getGrigliaInferiore()[6][6] = new Vuota(6, 6, true);

	    // Impostazione della cella come asciutta
	    game.setAsciutta(6, 6);

	    // Verifica che la cella sia stata impostata correttamente come asciutta
	    Vuota cellaVuota = (Vuota) game.getGrigliaInferiore()[6][6];
	    assertFalse(cellaVuota.getStato());
	  }

	  @Test
	  void testSpegniFornello() {
	    // Posizionamento di un Fornello iniziale in posizione (7, 7)
	    Fornello fornello = new Fornello(7, 7, false, true);
	    game.getGrigliaInferiore()[7][7] = fornello;
	    assertTrue(fornello.getStato());
	    // Spegnimento del Fornello in posizione (7, 7)
	    game.spegniFornello(7, 7);

	    // Verifica che lo stato del Fornello sia impostato su "spento"
	    assertFalse(fornello.getStato());
	  }

	  @Test
	  void testAccendiFornello() {
	    // Posizionamento di un Fornello iniziale in posizione (9, 9)
	    Fornello fornello = new Fornello(9, 9, false, false);
	    game.getGrigliaInferiore()[9][9] = fornello;
	    assertFalse(fornello.getStato());
	    // Accensione del Fornello in posizione (9, 9)
	    game.accendiFornello(9, 9);

	    // Verifica che lo stato del Fornello sia impostato su "acceso"
	    assertTrue(fornello.getStato());
	  }

	  @Test
	  void testChiudiRubinetto() {
	    // Posizionamento di un Rubinetto iniziale in posizione (5, 2)
	    Rubinetto rubinetto = new Rubinetto(5, 2, true, true);
	    game.getGrigliaInferiore()[5][2] = rubinetto;
	    assertTrue(rubinetto.getStato());
	    // Chiusura del Rubinetto in posizione (5, 2)
	    game.chiudiRubinetto(5, 2);

	    // Verifica che lo stato del Rubinetto sia impostato su "chiuso"
	    assertFalse(rubinetto.getStato());
	  }

	  @Test
	  void testChiudiLavatrice() {
	    // Posizionamento di una Lavatrice iniziale in posizione (2, 7)
	    Lavatrice lavatrice = new Lavatrice(2, 7, true, true);
	    game.getGrigliaInferiore()[2][7] = lavatrice;
	    assertTrue(lavatrice.getStato());
	    // Chiusura della Lavatrice in posizione (2, 7)
	    game.chiudiLavatrice(2, 7);

	    // Verifica che lo stato della Lavatrice sia impostato su "chiuso"
	    assertFalse(lavatrice.getStato());
	  }

}
