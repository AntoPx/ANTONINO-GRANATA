
import GameBoy.Controller;
import GameBoy.GameModel;
import GameBoy.GameView;
import GameBoy.SceltaView;

/***
 * @author Antonino Granata 200342023
 * @author Cecilia Tasca 20040354
 * @author Alberto Porzio  20038968
 */

public class Main {
	/**
	 * Costruttore 
	 */
	public Main() {
		
		GameView viewGame = new GameView(GameView.input());
		GameView viewGameChiaro =  new GameView(viewGame.getDim());
		SceltaView viewScelta = new SceltaView();
		GameModel modelGame = new GameModel(viewGame.getDim());
		Controller controller = new Controller(modelGame, viewGame, viewScelta, viewGameChiaro);
		
	}
	/**
	 * Questo metodo avvia l'intero programma
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}
}
