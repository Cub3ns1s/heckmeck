package heckmeck.server;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {

	// Attributes
	private Socket mClientSocket;
	private ObjectOutputStream mOos;
	private ObjectInputStream mOis;
	private Server mServer;
	private String mName;
	private SysoLog mLog;

	// Constructor
	public ClientConnection(Socket socket, Server server) throws IOException {
		mClientSocket = socket;
		mServer = server;
		mOos = new ObjectOutputStream(socket.getOutputStream());
		mOis = new ObjectInputStream(mClientSocket.getInputStream());
		mLog = new SysoLog();
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
			mOos.reset();
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
				ClientMessage clientMessage = (ClientMessage) mOis.readObject();
				System.out.println("CLCON:" + clientMessage.getMessageType());

				switch (clientMessage.getMessageType()) {
				case ClientMessage.LOGON:
					logon((LogonMessage) clientMessage);
					break;

				case ClientMessage.DECISION:
					mServer.move((DecisionMessage) clientMessage);
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
	 * Called after receiving the logon message from one of the clients
	 * 
	 * @param message
	 */
	private void logon(LogonMessage logonMessage) {
		mName = logonMessage.getName();
		mLog.log("New Player: " + mName);

		mServer.startGameIfAllClientsConnected();
	}

	/**
	 * shuts Client connection down
	 */
	public void shutdown() {
		try {
			mClientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return mName;
	}

}
