package heckmeck.client;

import heckmeck.server.LogonMessage;
import heckmeck.server.ServerMessage;
import heckmeck.server.WelcomeMessage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	// Attributes
	private Socket mSocket;
	private String mName;

	// Constructor
	public Client(String name) {
		mName = name;
	}

	/**
	 * Main Method - starts new Thread with new Client
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new Client(args[0])).start();
	}

	@Override
	public void run() {
		String ip = "127.0.0.1";
		try {
			mSocket = new Socket(ip, 23534);
			LogonMessage message = new LogonMessage(mName);

			ObjectOutputStream oos = new ObjectOutputStream(
					mSocket.getOutputStream());

			oos.writeObject(message);

			System.out.println("Client: Anmeldung abgeschickt");

			do {
				ObjectInputStream ios = new ObjectInputStream(
						mSocket.getInputStream());
				ServerMessage serverMessage = (ServerMessage) ios.readObject();

				processMessage(serverMessage);

			} while (true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * processes Message
	 * 
	 * @param serverMessage
	 */
	private void processMessage(ServerMessage serverMessage) {
		switch (serverMessage.getMessageType()) {
		case ServerMessage.WELCOME:
			processWelcomeMessage(serverMessage);
			break;

		default:
			break;
		}
	}

	/**
	 * processes Welcome Message
	 * 
	 * @param serverMessage
	 */
	private void processWelcomeMessage(ServerMessage serverMessage) {
		WelcomeMessage message = (WelcomeMessage) serverMessage;
		System.out.println(message.getText());
	}

}