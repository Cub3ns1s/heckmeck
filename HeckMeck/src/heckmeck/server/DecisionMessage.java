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

	public String getDots() {
		return mDots;
	}

	public boolean proceeds() {
		return mProceed;
	}

	public String toString() {
		return mDots + mProceed;
	}

	@Override
	public String getMessageType() {
		return mType;
	}
}