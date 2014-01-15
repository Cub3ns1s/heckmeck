/**
 * 
 */
package heckmeck.server;

import java.io.Serializable;

/**
 * @author lena.zein
 *
 */
public abstract class ServerMessage extends Message implements Serializable {

	public static final String WELCOME = "WELCOME";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8683906610289467286L;

	
}
