package heckmeck.client;

import java.util.Iterator;
import java.util.Scanner;

import heckmeck.server.DecisionMessage;
import heckmeck.server.GameState;
import heckmeck.server.PlayerState;

public class TextHeckmeck implements HeckmeckUI {
	
	private Client mClient; 
	private GameState mGameState;
	private String mName;
	
	public static void main( String[] args ){
		new TextHeckmeck(args[0]);
	}
	
	private TextHeckmeck( String name){

		mClient = new Client(name, this);
		mName = name; 
		mClient.run();
	}

	@Override
	public void update(GameState gameState) {
		mGameState = gameState;
		showMessage(gameState.toString());
		showMessage("Current player: " + mName);
		waitForUserInput( );
	}

	@Override
	public void showMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}

	/**
	 * waits for keyboard input from user to make move
	 */
	@SuppressWarnings("resource")
	private void waitForUserInput() {
		if (mGameState.getCurrentPlayer().getName().equals(mName)) {
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			if (input.length() != 2) {
				showMessage("Please repeat your decision! It has to contain two characters!");
			} else {
				mClient.createDecisionMessage( input );			

			}
		}
	}
//	for (Iterator<PlayerState> iterator = mGameState.getPlayerStates()
//			.iterator(); iterator.hasNext();) {
//		PlayerState playerState = iterator.next();
//		if (playerState.getName().equals(mName) && playerState.isTurn()) {
//			waitForUserInput();
//		}
//	}

}
