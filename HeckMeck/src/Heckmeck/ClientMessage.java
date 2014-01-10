package Heckmeck;

public abstract class ClientMessage {

	public static final String LOGON = "LOGON";
	public static final String MOVE  = "MOVE";
	
	public abstract String getMessageType() ;

}