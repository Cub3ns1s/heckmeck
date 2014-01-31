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
	private String mName;

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

	/**
	 * sends Message
	 * 
	 * @param message
	 */
	public void sendMessage(ServerMessage message) {
		try {
			mOos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets name of player
	 * 
	 * @return
	 */
	public String getName() {
		return mName;
	}

	/**
	 * waits for messages
	 */
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
					mServer.move((Decision) message);
					break;

				default:
					break;
				}

			} while (true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates logonMessage and logs player
	 * 
	 * @param message
	 */
	private void logon(ClientMessage message) {
		LogonMessage logonMessage = (LogonMessage) message;
		System.out.println("Logon Message erstellt");

		mName = logonMessage.getName();
		System.out.println("New Player: " + mName);

	}

	public void shutdown() {
		try {
			mClientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
