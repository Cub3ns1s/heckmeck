package heckmeck.server;

import java.io.IOException;

public class Log implements Loggable {

	@Override
	public void log(String message) {
		System.out.println(message);
	}

	@Override
	public void log(IOException e) {
		for (int i = 0; i < e.getStackTrace().length; i++) {
			StackTraceElement ste = e.getStackTrace()[i];
			log(ste.toString());
		}
	}

}
