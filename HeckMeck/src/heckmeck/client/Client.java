package heckmeck.client;

import heckmeck.exceptions.HeckmeckException;
import heckmeck.server.*;
import heckmeck.server.DecisionMessage;
import heckmeck.server.GameState;
import heckmeck.server.GameStateMessage;
import heckmeck.server.LogonMessage;
import heckmeck.server.ServerMessage;
import heckmeck.server.SysoLog;
import heckmeck.server.WelcomeMessage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	// Attributes
	private Socket mSocket;
	private String mName;
	private GameState mGameState;
	private ObjectOutputStream mOOS;
	private ObjectInputStream mOIS;
	private SysoLog mLog;

	// Constructor
	public Client(String name) {
		mName = name;
		mLog = new SysoLog();
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
		String ip = "127.0.0.1";
		initConnection(ip);
		logon();
		waitForServerMessages();
	}

	private void logon() {
		LogonMessage message = new LogonMessage(mName);

		sendMessage(message);

		mLog.log("Client: Anmeldung abgeschickt");
	}

	private void initConnection(String ip) {
		try {
			mSocket = new Socket(ip, 23534);
			mOOS = new ObjectOutputStream(mSocket.getOutputStream());
			mOIS = new ObjectInputStream(mSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(ClientMessage message) {
		try {
			mOOS.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void waitForServerMessages() {
		try {
			do {
				ServerMessage serverMessage = (ServerMessage) mOIS.readObject();
if (serverMessage != null) {

	processMessage(serverMessage);
}

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
		mGameState = gameStateMessage.getGameState();

		printGameState();
		mLog.log(mName);
		waitForUserInput();
	}

	private void printGameState() {
		mLog.log(mGameState.toString());
	}

	private void processFullMessage() throws HeckmeckException {
		mLog.log("Server full!");
		throw new HeckmeckException();
	}

	/**
	 * processes Welcome Message
	 * 
	 * @param serverMessage
	 */
	private void processWelcomeMessage(ServerMessage serverMessage) {
		WelcomeMessage message = (WelcomeMessage) serverMessage;
		mLog.log(message.getText());
	}

	private void waitForUserInput() {
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();

		int dots = input.charAt(0);
		boolean proceed = (input.charAt(1) == 'C');
		DecisionMessage decision = new DecisionMessage(dots, proceed);

		sendMessage(decision);

	}
}