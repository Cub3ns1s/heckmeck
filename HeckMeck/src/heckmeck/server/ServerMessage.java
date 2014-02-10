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
	public String mType;
	
}
