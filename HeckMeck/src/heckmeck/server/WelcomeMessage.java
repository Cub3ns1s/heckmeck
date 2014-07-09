package heckmeck.server;

public class WelcomeMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = -4708406812191908239L;
	private String mText;

	// Constructor
	public WelcomeMessage(String text) {
		super();
		this.mText = text;
		this.mType = WELCOME;
	}

	public String getText() {
		return mText;
	}

	@Override
	public String getMessageType() {
		return mType;
	}
}