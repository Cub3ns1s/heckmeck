package heckmeck.server;

import heckmeck.exceptions.*;

import java.util.*;

public class Game implements GameState {

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
		System.out.println(mCurrentPlayer.getName() + "'s turn!");
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

			if (decision.proceeds() == true) {
				mCurrentPlayer.getDiceState().fixValue(decision.getDots());

				if (mCurrentPlayer.getDiceState().getUnfixedDices().size() != 0) {
					mCurrentPlayer.getDiceState().dice();
				}

				mPlayers.set(getPlayerPosition(), mCurrentPlayer);

			} else if (decision.proceeds() == false) {
				validateThrowContainsWorm();
				validateThrowHigherExposedToken();
			}
		} catch (MisthrowException e) {
			e.printStackTrace();
		} catch (AlreadyFixedException e) {
			e.printStackTrace();
		} catch (ValueNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	private void validateThrowHigherExposedToken() throws MisthrowException {
		int amount = getThrowAmount();
		checkExposedTokens(amount);
	}

	private void checkExposedTokens(int amount) throws MisthrowException {

		if (!checkGrill(amount)) {
			if (!checkPlayerTopToken(amount)) {
				throw new MisthrowException();
			}
		}

	}

	private boolean checkPlayerTopToken(int amount) {
		for (int i = 0; i < mPlayers.size(); i++) {
			PlayerState playerState = mPlayers.get(i);
			try {
				if (amount == playerState.getDeck().getTopToken().getValue()) {
					return true;
				}
			} catch (NoTokenFoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private boolean checkGrill(int amount) {
		for (Iterator<Token> iterator = mGrill.getTokens().iterator(); iterator
				.hasNext();) {
			Token token = iterator.next();
			if (amount >= token.getValue()) {
				return true;
			}
		}
		return false;
	}

	private int getThrowAmount() {
		int amount = 0;

		for (Iterator<Dice> iterator = mCurrentPlayer.getDiceState()
				.getFixedDices().iterator(); iterator.hasNext();) {
			Dice dice = iterator.next();
			if (dice.getValue() == 6) {
				amount += 5;
			} else {
				amount += dice.getValue();
			}
		}
		return amount;
	}

	private void validateThrowContainsWorm() throws MisthrowException {
		for (Iterator<Dice> iterator = mCurrentPlayer.getDiceState()
				.getFixedDices().iterator(); iterator.hasNext();) {
			Dice dice = iterator.next();
			if (dice.getValue() == 6) {
				return;
			}
		}
		throw new MisthrowException();
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
