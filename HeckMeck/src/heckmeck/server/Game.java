package heckmeck.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements GameState {

	// Attributes
	private Grill mGrill;
	private List<PlayerState> mPlayers;

	// Constructor
	public Game(List<String> playerList) {

		initPlayerStates(playerList);

		mGrill = new Grill();
	}

	/**
	 * Main Method - prints players
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(args[0]);
	}

	/**
	 * inits player states
	 * 
	 * @param playerList
	 */
	private void initPlayerStates(List<String> playerList) {

		mPlayers = new ArrayList<PlayerState>();

		for (Iterator<String> iterator = playerList.iterator(); iterator
				.hasNext();) {
			String name = iterator.next();

			mPlayers.add(new PlayerState(name));
		}

	}

	/**
	 * modifys game state dependent on decision
	 * 
	 * @param decision
	 * @return
	 */
	public GameState move(Decision decision) {
		return this;
	}

	/**
	 * gets game state
	 * 
	 * @return
	 */
	public GameState getGameState() {
		return this;
	}

	@Override
	public Grill getGrill() {
		return mGrill;
	}

	@Override
	public List<PlayerState> getPlayerStates() {
		return mPlayers;
	}
}
