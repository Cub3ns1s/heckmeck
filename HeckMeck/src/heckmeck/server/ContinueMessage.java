package heckmeck.server;

import java.util.ResourceBundle;

public class ContinueMessage extends ServerMessage {

	private static final long serialVersionUID = -6821078629415858213L;
	private String mMessageID;
	
	
	public String getText() {
		ResourceBundle bundle = MessageTexts.getFile();
		return bundle.getString(mMessageID);
	}

	public ContinueMessage(String messageID) {
		this.mMessageID = messageID;
	}

	@Override
	public String getMessageType() {
		return CONTINUE;
	}

}
