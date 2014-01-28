package heckmeck.server;

public class WelcomeMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = -4708406812191908239L;
	private String mText;

	// Constructor
	public WelcomeMessage(String text) {
		mText = text;
	}

	@Override
	public String getMessageType() {
		return WELCOME;
	}

	/**
	 * gets text of message
	 * 
	 * @return
	 */
	public String getText() {
		return mText;
	}

}
