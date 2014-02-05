package heckmeck.server;

import java.io.Serializable;

public class DecisionMessage extends ClientMessage implements Serializable {

	// Attributes
	private static final long serialVersionUID = -7511837159574504708L;
	private int mDots;
	private boolean mProceed;

	// Constructor
	public DecisionMessage(int dots, boolean proceed) {
		super();
		this.mDots = dots;
		this.mProceed = proceed;
	}

	/**
	 * gets dots of dice
	 * 
	 * @return
	 */
	public int getDots() {
		return mDots;
	}

	/**
	 * decides whether player is proceeding
	 * 
	 * @return
	 */
	public boolean proceeds() {
		return mProceed;
	}

	@Override
	public String getMessageType() {
		return MOVE;
	}

}
