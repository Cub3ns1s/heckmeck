package heckmeck.server;

import heckmeck.exceptions.*;

import java.io.Serializable;
import java.util.*;

public class Game implements GameState, Serializable {

	// Attributes
	private static final long serialVersionUID = 8767133807326500588L;
	private Grill mGrill;
	private List<PlayerState> mPlayers;
	private PlayerState mCurrentPlayer;

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

		setCurrentPlayer(mPlayers.size() - 1);

	}

	/**
	 * sets current player
	 * 
	 * @param index
	 */
	private void setCurrentPlayer(int index) {
		mCurrentPlayer = mPlayers.get(index);
		mCurrentPlayer.setTurn(true);
	}

	private int getPlayerPosition() {
		return mPlayers.indexOf(mCurrentPlayer);
	}

	/**
	 * modifys game state dependent on decision
	 * 
	 * @param decision
	 * @return
	 */
	public GameState move(DecisionMessage decision) {
		try {
			
			mCurrentPlayer.getDiceState().fixValue(decision.getDots());
			mCurrentPlayer.getDiceState().dice();
		
			mPlayers.set(getPlayerPosition(), mCurrentPlayer);

		} catch (MisthrowException e) {
			e.printStackTrace();
		} catch (AlreadyFixedException e) {
			e.printStackTrace();
		} catch (ValueNotFoundException e) {
			e.printStackTrace();
		}
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

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append(mGrill.toString());

		for (Iterator<PlayerState> iterator = mPlayers.iterator(); iterator
				.hasNext();) {
			PlayerState playerState = iterator.next();
			sB.append(playerState.toString());
		}
		return sB.toString();
	}

}
