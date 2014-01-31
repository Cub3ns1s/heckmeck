package heckmeck.server;

import java.io.IOException;

public interface Logger {
	
	public void log(String message);
	
	public void log(IOException e);
}
