package heckmeck.server;

import java.io.Serializable;

public class PlayerState implements Serializable {

	// Attributes
	private static final long serialVersionUID = 620133243256754238L;
	private Deck mDeck;
	private String mName;
	private boolean mTurn;
	private DiceState mDiceState;

	
	// Constructor
	public PlayerState(String name) {
		this.mName = name;
		this.mTurn = false;
		this.mDeck = new Deck();
		this.mDiceState = new DiceState();
	}

	/**
	 * returns whether it's players turn or not
	 * 
	 * @return boolean
	 */
	public boolean isTurn() {
		return mTurn;
	}

	/**
	 * returns deck of player
	 * 
	 * @return Deck
	 */
	public Deck getDeck() {
		return mDeck;
	}

	/**
	 * returns name of player
	 * 
	 * @return String
	 */
	public String getName() {
		return mName;
	}

	/**
	 * returns dice state of player
	 * 
	 * @return DiceState
	 */
	public DiceState getDiceState() {
		return mDiceState;
	}

	/**
	 * sets if it's players turn or not
	 * 
	 * @param turn
	 */
	public void setTurn(boolean turn) {
		this.mTurn = turn;
	}

}
