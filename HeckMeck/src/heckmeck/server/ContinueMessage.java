package heckmeck.server;

import java.util.ResourceBundle;

public class ContinueMessage extends ServerMessage {

	// Attributes
	private static final long serialVersionUID = -6821078629415858213L;
	private String mMessageID;

	// Constructor
	public ContinueMessage(String messageID) {
		this.mMessageID = messageID;
	}

	public String getText() {
		ResourceBundle bundle = MessageTexts.getFile();
		return bundle.getString(mMessageID);
	}

	@Override
	public String getMessageType() {
		return CONTINUE;
	}
}