package Heckmeck;

import java.net.ServerSocket;

public class Client implements Runnable {
	
	@SuppressWarnings("unused")
	private ServerSocket mSocket;
	
	public Client(ServerSocket socket) {
		this.mSocket = socket;
		
	}

	@Override
	public void run() {
		waitForMessages();
		
	}
	
	private void waitForMessages(){
		
	}
	
}
