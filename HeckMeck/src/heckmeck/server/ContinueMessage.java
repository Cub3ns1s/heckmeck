package heckmeck.server;

public class ContinueMessage extends ServerMessage {

	private static final long serialVersionUID = 3639508661272578527L;
	private String mText;
	
	
	public String getmText() {
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
