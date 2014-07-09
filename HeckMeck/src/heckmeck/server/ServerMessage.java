/**
 * 
 */
package heckmeck.server;

public abstract class ServerMessage extends Message {

	// Attributes
	private static final long serialVersionUID = 2162459193783146887L;
	public static final String WELCOME = "WELCOME";
	public static final String FULL = "FULL";
	public static final String GAMESTATE = "GAMESTATE";
	public static final String CONTINUE = "CONTINUE";
	public static final String GAMEEND = "GAMEEND";
	public String mType;
}