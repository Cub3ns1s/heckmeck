package heckmeck.server;

public class GameStateMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = 479380160588633881L;
	private GameStateInterface mGameState;

	// Constructor
	public GameStateMessage(GameStateInterface gameState) {
		super();
		this.mGameState = gameState;
	}

	/**
	 * gets game state
	 * 
	 * @return
	 */
	public GameStateInterface getGameState() {
		return mGameState;
	}

	@Override
	public String getMessageType() {
		return null;
	}

}
