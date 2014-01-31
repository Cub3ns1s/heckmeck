package heckmeck.server;

import java.io.IOException;

public class DummyLog implements Logger {

	@Override
	public void log(String message) {

	}

	@Override
	public void log(IOException e) {

	}

}
