package heckmeck.client;

import java.util.Scanner;

import heckmeck.server.GameEndMessage;
import heckmeck.server.GameState;

public class TextHeckmeck implements HeckmeckUI {

	// Attributes
	private Client mClient;
	private GameState mGameState;
	private String mName;

	//Constructor
	public static void main(String[] args) {
		new TextHeckmeck(args[0]);
	}

	private TextHeckmeck(String name) {
		mClient = new Client(name, this, "127.0.0.1");
		mName = name;
		mClient.run();
	}

	private void waitForUserInput() {
		if (mGameState.getCurrentPlayer().getName().equals(mName)) {
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();

			if (input.length() != 2) {
				showMessage("Please repeat your decision! It has to contain two characters!");
			} else {
				mClient.createDecisionMessage(input);

			}
			
			scanner.close();
		}
	}
	
	@Override
	public void update(GameState gameState) {
		mGameState = gameState;
		showMessage(gameState.toString());
		showMessage("Current player: " + mName);
		waitForUserInput();
	}

	@Override
	public void showMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void endGame(GameEndMessage gameEndMessage) {

	}

}