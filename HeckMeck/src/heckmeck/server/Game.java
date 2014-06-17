package heckmeck.server;

import heckmeck.exceptions.*;

import java.util.*;

public class Game implements GameState {

	// Attributes
	private static final long serialVersionUID = 8767133807326500588L;
	private Grill mGrill;
	private List<PlayerState> mPlayers;
	private PlayerState mCurrentPlayer;
	private transient ClientManagement mClientManagement;

	// Constructor
	public Game(List<String> playerList, ClientManagement clientManagement) {
		initPlayerStates(playerList);
		mClientManagement = clientManagement;
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
	public GameState move(DecisionMessage decision, String playerName) {
		if (mCurrentPlayer.getName() != playerName) {

			ContinueMessage continueMessage = new ContinueMessage( "M007" );
			mClientManagement.sendMessage(continueMessage);
		} else {
			try {

				if (!decision.getDots().equals("0")) {
					mCurrentPlayer.getDiceState().fixValue(decision.getDots());
				}

				if (decision.proceeds()) {
					continueTurn();
				} else {
					finalizeTurn();
				}

			} catch (AlreadyFixedException e) {
				ContinueMessage continueMessage = new ContinueMessage( "M008" );
				mClientManagement.sendMessage(continueMessage);

			} catch (ValueNotFoundException e) {
			}
		}

		return this;
	}

	private void finalizeTurn() {
		try {
			validateThrowContainsWorm();
			transferTokenToCurrentPlayer();

		} catch (MisthrowDecisionException e) {
			handleMissthrowDecisionException();
		}

		isEndOfGame();
	}

	private void isEndOfGame() {
		if (!mGrill.hasActiveTokens()) {
			// Spiel zu Ende!
			prepareEndofGame();
		}
	}

	private void prepareEndofGame() {
		GameEndMessage gameEndMessage = new GameEndMessage(mPlayers);
		mClientManagement.sendMessage(gameEndMessage);
	}

	private void handleMissthrowDecisionException() {
		if (mCurrentPlayer.getDiceState().getUnfixedDices().size() != 0) {
			try {
				mCurrentPlayer.getDiceState().dice();

				ContinueMessage continueMessage = new ContinueMessage("M011");
				mClientManagement.sendMessage(continueMessage);

			} catch (MisthrowThrowException e) {
				handleMisthrowException();
			}

		} else {
			executeMissthrowConsequences();
		}

	}

	private void continueTurn() {
		if (mCurrentPlayer.getDiceState().getUnfixedDices().size() != 0) {
			try {
				mCurrentPlayer.getDiceState().dice();

			} catch (MisthrowThrowException e) {
				handleMisthrowException();
			}
		}

		else {
			finalizeTurn();
		}
	}

	private void handleMisthrowException() {
		executeMissthrowConsequences();

		ContinueMessage continueMessage = new ContinueMessage("M015");
		mClientManagement.sendMessage(continueMessage);
	}

	private void executeMissthrowConsequences() {
		mGrill.failure(mCurrentPlayer.getDeck().getTopToken());
		mCurrentPlayer.getDeck().removeTopToken();
		mGrill.deactivateHighestToken();
		setNextTurn();
				
		isEndOfGame();

	}

	private void setNextTurn() {
		mCurrentPlayer.setTurn(false);
		mCurrentPlayer.getDiceState().clear();

		if (getPlayerPosition() == (mPlayers.size() - 1)) {
			setCurrentPlayer(0);
		} else {
			setCurrentPlayer((getPlayerPosition() + 1));
		}

		ContinueMessage continueMessage = new ContinueMessage("M006");
		mClientManagement.sendMessage(continueMessage);

	}

	private void transferTokenToCurrentPlayer()
			throws MisthrowDecisionException {

		int amount = getThrowAmount();

		if ((!transferTokenFromOtherPlayer(amount))
				&& (!transferTokenFromGrill(amount))) {
			throw new MisthrowDecisionException();
		} else {
			setNextTurn();
		}
	}

	private boolean transferTokenFromOtherPlayer(int amount) {
		Token token = null;

		for (int i = 0; i < mPlayers.size(); i++) {
			if (i != getPlayerPosition()) {
				PlayerState playerState = mPlayers.get(i);
				token = playerState.getDeck().getTopToken();
				if ((token != null) && (amount == token.getValue())) {
					transferTokenFromPlayer(token, playerState);
					return true;
				}
			}
		}
		return false;
	}

	private void transferTokenFromPlayer(Token token, PlayerState playerState) {
		mCurrentPlayer.getDeck().addToken(token);
		playerState.getDeck().removeTopToken();
	}

	private boolean transferTokenFromGrill(int amount) {
		Token tmpToken = null;

		for (Iterator<Token> iterator = mGrill.getTokens().iterator(); iterator
				.hasNext();) {
			Token token = iterator.next();

			if (token.isActive() && amount >= token.getValue()) {
				tmpToken = token;
			}
		}

		if (tmpToken != null) {
			try {
				mGrill.remove(tmpToken.getValue());
			} catch (NoTokenFoundException e) {
				e.printStackTrace();
			}

			mCurrentPlayer.getDeck().addToken(tmpToken);
			return true;
		} else {
			return false;
		}
	}

	private int getThrowAmount() {
		int amount = 0;
		List<Dice> fixedDices = mCurrentPlayer.getDiceState().getFixedDices();

		for (int i = 0; i < fixedDices.size(); i++) {
			Dice dice = mCurrentPlayer.getDiceState().getFixedDices().get(i);
			amount += dice.getValue();
		}

		return amount;
	}

	private void validateThrowContainsWorm() throws MisthrowDecisionException {
		List<Dice> fixedDices = mCurrentPlayer.getDiceState().getFixedDices();

		for (int i = 0; i < fixedDices.size(); i++) {
			Dice dice = mCurrentPlayer.getDiceState().getFixedDices().get(i);

			if (dice.getLabel().equals("W")) {
				return;
			}
		}

		throw new MisthrowDecisionException();
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
	public PlayerState getCurrentPlayer() {
		return mCurrentPlayer;
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
