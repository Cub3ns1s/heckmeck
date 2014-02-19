package heckmeck.client;

import heckmeck.exceptions.*;
import heckmeck.server.*;

import java.io.*;
import java.net.*;
import java.util.*;

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

	/**
	 * starts client
	 */
	private void start() {
		String ip = "172.17.40.29";
		initConnection(ip);
		logon();
		waitForServerMessages();
	}

	/**
	 * logs client on and sends message to server
	 */
	private void logon() {
		LogonMessage logonMessage = new LogonMessage(mName);

		sendMessage(logonMessage);
	}

	/**
	 * inits connection of client
	 * 
	 * @param ip
	 */
	private void initConnection(String ip) {
		try {
			mSocket = new Socket(ip, 23534);
			mOOS = new ObjectOutputStream(mSocket.getOutputStream());
			mOIS = new ObjectInputStream(mSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sends message to server
	 * 
	 * @param message
	 */
	private void sendMessage(ClientMessage message) {
		try {
			mOOS.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * waits for message from server
	 */
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
			break;

		case ServerMessage.CONTINUE:
			processContinueMessage(serverMessage);
			break;

		default:
			break;
		}
	}

	private void processContinueMessage(ServerMessage serverMessage) {
		ContinueMessage continueMessage = (ContinueMessage) serverMessage;
		mLog.log(continueMessage.getText());
	}

	/**
	 * processes GameStateMessage
	 * 
	 * @param serverMessage
	 */
	private void processGameStateMessage(ServerMessage serverMessage) {
		GameStateMessage gameStateMessage = (GameStateMessage) serverMessage;
		mGameState = gameStateMessage.getGameState();

		printGameState();
		mLog.log("Current player: " + mName);

		for (Iterator<PlayerState> iterator = mGameState.getPlayerStates()
				.iterator(); iterator.hasNext();) {
			PlayerState playerState = iterator.next();
			if (playerState.getName().equals(mName) && playerState.isTurn()) {
				waitForUserInput();
			}
		}
	}

	/**
	 * prints Game State
	 */
	private void printGameState() {
		mLog.log(mGameState.toString());
		new GUIHeckmeck(mGameState);
	}

	/**
	 * processes Message when server is full
	 * 
	 * @throws HeckmeckException
	 */
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

	/**
	 * waits for keyboard input from user to make move
	 */
	@SuppressWarnings("resource")
	private void waitForUserInput() {
		if (mGameState.getCurrentPlayer().getName().equals(mName)) {
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			
			if (input.length() != 2) {
				mLog.log("Please repeat your decision! It has to contain two characters!");
			} else {
				input = input.toUpperCase();
				String dots = input.substring(0, 1);
				boolean proceed = (input.charAt(1) == 'C');
				DecisionMessage decision = new DecisionMessage(dots, proceed);

				sendMessage(decision);
			}
		}
	}
}