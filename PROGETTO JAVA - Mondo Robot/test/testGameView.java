package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameBoy.GameView;

class testGameView {

	private static int DIMENSIONE = 12;
	GameView game;
	
	@BeforeEach
	void testInizializzaGameView() {
		game = new GameView(DIMENSIONE);
	}
	
	@Test
	void testInput() {
	    
	    int dimensione = game.getDim();

	    // Verifica che la dimensione sia stata impostata correttamente
	    assertEquals(DIMENSIONE, dimensione);
	}

	// fare test creaGriglia
	@Test
	void testCreaGrilia() {
		for (int i = 0; i < game.getDim(); i++) {
			for (int j = 0; j < game.getDim(); j++) {
				assertNotNull(game.getGrigliaSuperiore()[i][j].getClass());
				assertTrue(game.getGrigliaSuperiore()[i][j] instanceof JButton);
			}
		}
	}
	
	
	@Test
	void testCaricaImmagini() {
	   
	    try {
	        game.caricaImmagini();
	    } catch (IOException e) {
	        fail("Errore durante il caricamento delle immagini");
	    }

	    // Verifica che tutte le immagini siano state caricate correttamente
	    assertNotNull(game.acqua);
	    assertNotNull(game.animale);
	    assertNotNull(game.fornelloAcceso);
	    assertNotNull(game.fornelloSpento);
	    assertNotNull(game.lavatrice);
	    assertNotNull(game.lavatriceGuasta);
	    assertNotNull(game.mattoni);
	    assertNotNull(game.robot);
	    assertNotNull(game.rubinettoChiuso);
	    assertNotNull(game.rubinettoAperto);
	}
	
	@Test
	void testInizializzaMuri() {
	   
	    game.inizializzaMuri(DIMENSIONE);

	    // Verifica che gli angoli della griglia superiore abbiano l'icona del muro (mattoni)
	    ImageIcon muro = new ImageIcon(game.mattoni);
	    assertEquals(muro.getClass(), game.getGrigliaSuperiore()[0][0].getIcon().getClass());
	    assertEquals(muro.getClass(), game.getGrigliaSuperiore()[0][4].getIcon().getClass());
	    assertEquals(muro.getClass(), game.getGrigliaSuperiore()[4][0].getIcon().getClass());
	    assertEquals(muro.getClass(), game.getGrigliaSuperiore()[5][0].getIcon().getClass());
	}

	


}
