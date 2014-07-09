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

		try {
			waitForMessage();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void waitForMessages() {
		try {
			do {
				waitForMessage();
			} while (true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void waitForMessage() throws IOException, ClassNotFoundException {
		ClientMessage clientMessage = (ClientMessage) mOis.readObject();
		mLog.log(clientMessage.toString());

		switch (clientMessage.getMessageType()) {
		case ClientMessage.LOGON:
			logon((LogonMessage) clientMessage);
			break;

		case ClientMessage.DECISION:
			mServer.move((DecisionMessage) clientMessage, mName);
			break;

		default:
			break;
		}
	}

	public void sendMessage(ServerMessage message) {
		try {
			mOos.reset();
			mOos.writeObject(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void logon(LogonMessage logonMessage) {
		mName = logonMessage.getName();
		mLog.log(MessageTexts.getMessage("M005") + mName);
	}

	public void shutdown() {
		try {
			mClientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return mName;
	}

	@Override
	public void run() {
		waitForMessages();
	}

	@Override
	public String toString() {
		return mName;
	}
}