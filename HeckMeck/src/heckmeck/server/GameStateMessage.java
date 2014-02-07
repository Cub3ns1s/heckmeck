package heckmeck.server;

public class GameStateMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = 479380160588633881L;
	private GameState mGameState;

	// Constructor
	public GameStateMessage(GameState gameState) {
		super();
		this.mGameState = gameState;
	}

	/**
	 * gets game state
	 * 
	 * @return
	 */
	public GameState getGameState() {
		return mGameState;
	}

	@Override
	public String getMessageType() {
		return GAMESTATE;
	}

}
