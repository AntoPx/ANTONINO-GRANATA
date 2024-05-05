package Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import GameBoy.Controller;
import GameBoy.GameModel;
import GameBoy.GameView;
import GameBoy.SceltaView;

class testController {

	private static final int DIMENSIONE = 12; // La dimensione della matrice da testare

	GameView view = new GameView(DIMENSIONE);
	GameView chiaro = new GameView(DIMENSIONE);
	GameModel game = new GameModel(DIMENSIONE);
	SceltaView scelta = new SceltaView();
	Controller crt = new Controller(game, view, scelta, chiaro);

	@Test
	void testNotNull() {
		assertNotNull(view);
		assertNotNull(chiaro);
		assertNotNull(game);
		assertNotNull(scelta);
		assertNotNull(crt);
	}

	@Test
	void testMuoviRobotSullaStessaCella() {
		int rRobot = crt.model.getR();
		int cRobot = crt.model.getC();
		crt.muoviRobot(rRobot, cRobot);

		assertTrue(rRobot == crt.model.gino.getR());
		assertTrue(cRobot == crt.model.gino.getC());

	}

	@Test
	void testMuoviRobotSuCellaAdicenteModificandoLaRiga() {
		int rRobot = crt.model.getR();
		int cRobot = crt.model.getC();
		if (game.muoviRobot(rRobot + 1, cRobot) == true) {
			crt.muoviRobot(rRobot + 1, cRobot);
			assertTrue(rRobot != crt.model.gino.getR());
			assertTrue(cRobot == crt.model.gino.getC());
		}else {
			assertTrue(rRobot == crt.model.gino.getR());
			assertTrue(cRobot == crt.model.gino.getC());
		}
	}
	
	@Test
	 void testAvviaThAnimaleDomestico() throws InterruptedException {
	    
		 int r= game.cane.getR();
		 int c = game.cane.getC();
		 
	    // Avvia il metodo da testare in un nuovo thread
	    Thread thread = new Thread(() -> crt.avviaThAnimaleDomestico());
	    thread.start();
	    
	    // Attendi un certo periodo di tempo (ad esempio 1 secondo) per consentire l'esecuzione del thread
	    Thread.sleep(1000);
	    
	    // verifico che le cordinate del cane siano cambiate
	    assertTrue(r!= game.cane.getR() || c!= game.cane.getC());
	    
	  }
	
	

	@Test
	void test() throws InterruptedException {
		Controller.avviaThPerditaAcqua perdita;
		int r = 4, c = 4;	//Coordinate della cella da dove far iniziare la perdita
		int time = 10;		//Timer della perdiata
		
		perdita = crt.new avviaThPerditaAcqua(r, c, time);
		perdita.avviaTh(r, c, time);
		Thread.sleep(50000);
		assertTrue(perdita.getR()!=r || perdita.getC()!=c);
	}

}
