package heckmeck.client;

import heckmeck.exceptions.HeckmeckException;
import heckmeck.server.GameState;
import heckmeck.server.GameStateMessage;
import heckmeck.server.LogonMessage;
import heckmeck.server.ServerMessage;
import heckmeck.server.WelcomeMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

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
		new Client(args[0]).start();
	}

	private void start() {
		logon();
		waitForServerMessages();

	}

	private void logon() {
		String ip = "127.0.0.1";

		try {
			mSocket = new Socket(ip, 23534);

			LogonMessage message = new LogonMessage(mName);

			ObjectOutputStream oos = new ObjectOutputStream(
					mSocket.getOutputStream());

			oos.writeObject(message);

			System.out.println("Client: Anmeldung abgeschickt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void waitForServerMessages() {
		try {

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
		} catch (HeckmeckException e) {
		}

	}

	/**
	 * processes Message
	 * 
	 * @param serverMessage
	 * @throws HeckmeckException
	 */
	private void processMessage(ServerMessage serverMessage)
			throws HeckmeckException {
		switch (serverMessage.getMessageType()) {
		case ServerMessage.WELCOME:
			processWelcomeMessage(serverMessage);
			break;
		case ServerMessage.FULL:
			processFullMessage();
			break;
		case ServerMessage.GAMESTATE:
			processGameStateMessage(serverMessage);

		default:
			break;
		}
	}

	private void processGameStateMessage(ServerMessage serverMessage) {
		GameStateMessage gameStateMessage = (GameStateMessage) serverMessage;
		GameState gameState = gameStateMessage.getGameState();

		printGameState(gameState);

	}

	private void printGameState(GameState gameState) {
		// TODO Auto-generated method stub

	}

	private void processFullMessage() throws HeckmeckException {
		System.out.println("Server full!");
		throw new HeckmeckException();

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