package Heckmeck;

import java.io.Serializable;

public class LogonMessage implements Serializable {

	private static final long serialVersionUID = -2490711427776617424L;
	private String mName;
	
	public LogonMessage(String name) {
		this.mName = name;
	}

	public String getName() {
		return mName;
	}
	
	
	
	
}
