package heckmeck.server;

public abstract class ClientMessage extends Message {

	private static final long serialVersionUID = -2006623983852928572L;
	// Attributes
	public static final String LOGON = "LOGON";
	public static final String DECISION = "DECISION";
	public String mType;
}