package heckmeck.server;

public class GameStateMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = 479380160588633881L;
	private GameStateable mGameState;

	// Constructor
	public GameStateMessage(GameStateable gameState) {
		super();
		this.mGameState = gameState;
	}

	/**
	 * gets game state
	 * 
	 * @return
	 */
	public GameStateable getGameState() {
		return mGameState;
	}

	@Override
	public String getMessageType() {
		return null;
	}

}
