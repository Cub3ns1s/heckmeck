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

	public boolean isTurn() {
		return mTurn;
	}

	public Deck getDeck() {
		return mDeck;
	}

	public String getName() {
		return mName;
	}

	public DiceState getDiceState() {
		return mDiceState;
	}

	public void setTurn(boolean turn) {
		this.mTurn = turn;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("Player: " + this.getName() + "\n");
		sB.append("Tokens: " + mDeck.getSize() + "\n");
		if (mDeck.getTopToken() != null)
			sB.append("Top Token: " + mDeck.getTopToken().toString() + "\n");

		if (isTurn()) {
			sB.append(mDiceState.toString());
		}
		sB.append("\n********************\n");
		return sB.toString();
	}
}