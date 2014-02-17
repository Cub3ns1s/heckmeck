package heckmeck.server;

public class ContinueMessage extends ServerMessage {

	private static final long serialVersionUID = -6821078629415858213L;
	private String mText;
	
	
	public String getText() {
		return mText;
	}

	public ContinueMessage(String text) {
		this.mText = text;
	}

	@Override
	public String getMessageType() {
		return CONTINUE;
	}

}
