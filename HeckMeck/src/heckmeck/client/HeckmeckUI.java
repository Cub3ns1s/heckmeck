package heckmeck.client;

import heckmeck.server.GameState;

public interface HeckmeckUI {
	
	public void update(GameState gameState);
	
	public void showMessage(String message);

}
