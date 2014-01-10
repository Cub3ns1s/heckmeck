package Heckmeck;

public abstract class ClientMessage {

	// Attributes
	public static final String LOGON = "LOGON";
	public static final String MOVE = "MOVE";

	/**
	 * abstract method for getting the message type
	 * 
	 * @return
	 */
	public abstract String getMessageType();

}