package heckmeck.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientListener implements Runnable {

	// Attributes
	private Socket mClientSocket;
	private ObjectOutputStream mOos;
	private ObjectInputStream mOis;

	
	// Constructor
	public ClientListener(Socket socket) throws IOException {
		mClientSocket = socket;
		mOos = new ObjectOutputStream(socket.getOutputStream());
		mOis = new ObjectInputStream(mClientSocket.getInputStream());
	}

	@Override
	public void run() {
		waitForMessages();
	}

	private void waitForMessages() {
		try {
			do {				
				ClientMessage message = (ClientMessage) mOis.readObject();
			
				switch (message.getMessageType()) {
				case ClientMessage.LOGON:
					System.out.println("Message Typ 'LOGON' empfangen");
					logon(message);
					break;
					
				case ClientMessage.MOVE:
					move(message);
					break;

				default:
					break;
				}

			} while (true);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			System.out.println("No message in object found");
			e.printStackTrace();
		}
	}

	private void move(ClientMessage message) {

	}

	private void logon(ClientMessage message) {
		LogonMessage logonMessage =(LogonMessage) message;
		System.out.println("Logon Message erstellt");

		System.out.println("New Player: " + logonMessage.getName());

	}
}
