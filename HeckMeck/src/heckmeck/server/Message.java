package heckmeck.server;

import java.io.Serializable;
import java.util.Date;

public abstract class Message implements Serializable {

	
	private static final long serialVersionUID = -131112927509528606L;
	protected Date mDate;
	
	
	public Date getDate() {
		return mDate;
	}	
	
	/**
	 * abstract method for getting the message type
	 * 
	 * @return
	 */
	public abstract String getMessageType();
	
	
	public String toString() {		
		return "MESSAGE: " + getMessageType() + "; CREATED: " + mDate;
	}

}
