package heckmeck.server;

public class DecisionMessage extends ClientMessage {

	// Attributes
	private static final long serialVersionUID = -7511837159574504708L;
	private String mDots;
	private boolean mProceed;

	// Constructor
	public DecisionMessage(String dots, boolean proceed) {
		super();
		this.mDots = dots;
		this.mProceed = proceed;
		this.mType = DECISION;
	}

	/**
	 * gets dots of dice
	 * 
	 * @return
	 */
	public String getDots() {
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
		return mType;
	}
	
	public String toString() {
		return mDots + mProceed;
	}

}
