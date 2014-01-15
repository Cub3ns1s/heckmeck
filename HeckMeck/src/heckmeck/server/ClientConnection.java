package heckmeck.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements Runnable {

	// Attributes
	private Socket mClientSocket;
	private ObjectOutputStream mOos;
	private ObjectInputStream mOis;
	private Server mServer;

	
	// Constructor
	public ClientConnection(Socket socket, Server server) throws IOException {
		mClientSocket = socket;
		mServer = server; 
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

	public void sendMessage(ServerMessage message) {
		// TODO Auto-generated method stub
		try {
			mOos.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
