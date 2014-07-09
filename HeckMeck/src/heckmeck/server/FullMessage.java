package heckmeck.server;

public class FullMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = 6798274015137248667L;

	// Constructor
	public FullMessage() {
		super();
		this.mType = FULL;
	}

	@Override
	public String getMessageType() {
		return mType;
	}
}