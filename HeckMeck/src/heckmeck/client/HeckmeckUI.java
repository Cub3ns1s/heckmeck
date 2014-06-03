package heckmeck.client;

import heckmeck.server.GameEndMessage;
import heckmeck.server.GameState;

public interface HeckmeckUI {
	
	public void update(GameState gameState);
	
	public void showMessage(String message);
	
	public void endGame(GameEndMessage gameEndMessage);

}
