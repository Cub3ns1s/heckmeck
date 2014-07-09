package heckmeck.server;

import java.io.Serializable;
import java.util.Date;

public abstract class Message implements Serializable {

	// Attributes
	private static final long serialVersionUID = -131112927509528606L;
	protected Date mDate;

	public Date getDate() {
		return mDate;
	}

	public String toString() {
		return "MESSAGE: " + getMessageType() + "; CREATED: " + mDate;
	}

	public abstract String getMessageType();
}