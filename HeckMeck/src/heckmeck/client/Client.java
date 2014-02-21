package heckmeck.client;

import heckmeck.exceptions.*;
import heckmeck.server.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable{

	// Attributes
	private Socket mSocket;
	private String mName;
	private GameState mGameState;
	private ObjectOutputStream mOOS;
	private ObjectInputStream mOIS;
	private SysoLog mLog;
	private HeckmeckUI mUI;

	// Constructor
	public Client(String name, HeckmeckUI ui) {
		mName = name;
		mLog = new SysoLog();
		mUI = ui;
	}

//	/**
//	 * Main Method - starts new Thread with new Client
//	 * 
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		new Client(args[0]).start();
//	}

	/**
	 * starts client
	 */
	public void run() {
		String ip = "127.0.0.1";
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


	}

	/**
	 * prints Game State
	 */
	private void printGameState() {
		mUI.update(mGameState);
	}

	/**
	 * processes Message when server is full
	 * 
	 * @throws HeckmeckException
	 */
	private void processFullMessage() throws HeckmeckException {
		mUI.showMessage("Server full!");
		throw new HeckmeckException();
	}

	/**
	 * processes Welcome Message
	 * 
	 * @param serverMessage
	 */
	private void processWelcomeMessage(ServerMessage serverMessage) {
		WelcomeMessage message = (WelcomeMessage) serverMessage;
		mUI.showMessage(message.getText());
	}

	public void createDecisionMessage(String input) {
		String decisionString = input.toUpperCase();
		String dots = decisionString.substring(0, 1);
		boolean proceed = (decisionString.charAt(1) == 'C');
		DecisionMessage decision = new DecisionMessage(dots, proceed);

		sendMessage(decision);
	}

}