package heckmeck.server;

public class LogonMessage extends ClientMessage {

	// Attributes
	private static final long serialVersionUID = -2490711427776617424L;
	private String mName;

	// Constructor
	public LogonMessage(String name) {
		this.mName = name;
		this.mType = LOGON;
	}

	public String getName() {
		return mName;
	}

	@Override
	public String getMessageType() {
		return mType;
	}
}