package heckmeck.server;

import java.io.IOException;

public class SysoLog {

	public void log(String message) {
		System.out.println(message);
	}

	public void log(IOException e) {
		for (int i = 0; i < e.getStackTrace().length; i++) {
			StackTraceElement ste = e.getStackTrace()[i];
			log(ste.toString());
		}
	}
}