/**
 * 
 */
package heckmeck.server;

import java.io.Serializable;

public abstract class ServerMessage extends Message implements Serializable {

	// Attributes
	private static final long serialVersionUID = 8683906610289467286L;
	public static final String WELCOME = "WELCOME";
	public static final String FULL = "FULL";
	public static final String GAMESTATE = "GAMESTATE";

}
