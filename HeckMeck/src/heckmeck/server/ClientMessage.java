package heckmeck.server;

public abstract class ClientMessage extends Message {

	// Attributes
	private static final long serialVersionUID = -2006623983852928572L;
	public static final String LOGON = "LOGON";
	public static final String DECISION = "DECISION";
	public String mType;
}