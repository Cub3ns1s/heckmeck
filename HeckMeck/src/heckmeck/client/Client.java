package heckmeck.client;

import heckmeck.exceptions.*;
import heckmeck.server.*;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class Client implements Runnable {

	// Attributes
	private Socket mSocket;
	private String mName;
	private GameState mGameState;
	private ObjectOutputStream mOOS;
	private ObjectInputStream mOIS;
	private HeckmeckUI mUI;
	private String mServerIP;

	//Constructor
	public Client(String name, HeckmeckUI ui, String ip) {
		mName = name;
		mUI = ui;
		mServerIP = ip;
	}

	public void run() {
		initConnection(mServerIP);
		logon();
		waitForServerMessages();
	}

	private void logon() {
		LogonMessage logonMessage = new LogonMessage(mName);
		sendMessage(logonMessage);
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

		case ServerMessage.GAMEEND:
			processGameEndMessage(serverMessage);
			
		default:
			break;
		}
	}

	private void processGameEndMessage(ServerMessage serverMessage) {
		GameEndMessage gameEndMessage = (GameEndMessage) serverMessage;
		mUI.endGame(gameEndMessage);
	}

	private void processContinueMessage(ServerMessage serverMessage) {
		ContinueMessage continueMessage = (ContinueMessage) serverMessage;
		mUI.showMessage(continueMessage.getText());
	}

	private void processGameStateMessage(ServerMessage serverMessage) {
		GameStateMessage gameStateMessage = (GameStateMessage) serverMessage;
		mGameState = gameStateMessage.getGameState();

		printGameState();
	}

	private void processFullMessage() throws HeckmeckException {
		JOptionPane.showMessageDialog(null, MessageTexts.getMessage("M001"),
				"Server error", JOptionPane.ERROR_MESSAGE);

		throw new HeckmeckException();
	}

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

	private void printGameState() {
		mUI.update(mGameState);
	}

}