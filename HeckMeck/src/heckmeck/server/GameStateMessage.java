package heckmeck.server;

public class GameStateMessage extends ServerMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 479380160588633881L;
	private GameState mGameState;
	
	
	
	
	public GameStateMessage(GameState gameState) {
		super();
		this.mGameState = gameState;
	}
	
	

	public GameState getGameState() {
		return mGameState;
	}





	@Override
	public String getMessageType() {
		// TODO Auto-generated method stub
		return null;
	}

}
