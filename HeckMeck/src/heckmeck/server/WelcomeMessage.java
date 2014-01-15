package heckmeck.server;

public class WelcomeMessage extends ServerMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4708406812191908239L;
	private String mText; 
	
	public WelcomeMessage(String text){
		mText = text;
	}
	@Override
	public String getMessageType() {
		
		return WELCOME;
	}
	
	public String getText(){
		return mText;
	}

}
