package heckmeck.server;

import java.io.Serializable;

public class Decision extends ClientMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511837159574504708L;
	private int mDots;
	private boolean mProceed;
	
	public Decision(int dots, boolean proceed) {
		super();
		this.mDots = dots;
		this.mProceed = proceed;
	}
	
	public int getDots() {
		return mDots;
	}
	
	public boolean proceeds() {
		return mProceed;
	}

	@Override
	public String getMessageType() {
		return MOVE;
	}
	
	
}
