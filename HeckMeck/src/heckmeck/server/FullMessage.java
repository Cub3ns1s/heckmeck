package heckmeck.server;

import java.io.Serializable;

public class FullMessage extends ServerMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6798274015137248667L;

	@Override
	public String getMessageType() {
		return FULL;
	}

}
