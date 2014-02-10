package heckmeck.server;

public class DecisionMessage extends ClientMessage {

	// Attributes
	private static final long serialVersionUID = -7511837159574504708L;
	private int mDots;
	private boolean mProceed;

	// Constructor
	public DecisionMessage(int dots, boolean proceed) {
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
		return mType;
	}

}
