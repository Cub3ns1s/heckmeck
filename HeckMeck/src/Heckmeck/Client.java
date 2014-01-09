package Heckmeck;

import java.net.ServerSocket;

public class Client implements Runnable {
	
	//Attributes
	private ServerSocket mSocket;
	private Server mServer;
	
	
	//Constructor
	public Client(ServerSocket socket, Server server) {
		this.mSocket = socket;
		this.mServer = server;
	}

	@Override
	public void run() {
		waitForMessages();
	}
	
	private void waitForMessages(){
		
	}
	
}
