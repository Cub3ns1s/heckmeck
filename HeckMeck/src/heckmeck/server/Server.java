package heckmeck.server;

import heckmeck.client.HeckmeckUI;
import heckmeck.exceptions.WrongPlayerCountException;

import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Server implements Runnable {

	// Attributes
	private ServerSocket mServerSocket;
	private ClientManagement mClientManagement;
	private Game mGame;
	private int mPlayerCount;
	private static final int MINPLAYER = 2;
	private static final int MAXPLAYER = 4;
	private boolean mRunning = true;
	private HeckmeckUI mUI;
	// private SysoLog mLog;

	// Constructor
	public Server(int playerCount, HeckmeckUI ui) {
		mPlayerCount = playerCount;
		mUI = ui;
		mClientManagement = new ClientManagement(mPlayerCount);
		// mLog = new SysoLog();

		new MessageTexts("English");
	}

	public void move(DecisionMessage decision, String playerName) {
		GameStateMessage gameStateMessage = new GameStateMessage(mGame.move(
				decision, playerName));

		mClientManagement.sendMessage(gameStateMessage);
	}

	public void checkPlayerCount(int playerCount)
			throws WrongPlayerCountException {
		if (playerCount < MINPLAYER) {
			mUI.showMessage(MessageTexts.getMessage("M016") + " " + MINPLAYER);
			throw new WrongPlayerCountException();
		}

		if (playerCount > MAXPLAYER) {
			mUI.showMessage(MessageTexts.getMessage("M017") + " " + MAXPLAYER);
			throw new WrongPlayerCountException();
		}
	}

	public void run() {
		try {
			mServerSocket = new ServerSocket(23534);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			checkPlayerCount(mPlayerCount);
		} catch (WrongPlayerCountException e1) {
			return;
		}

		// mLog.log(MessageTexts.getMessage("M018") + mPlayerCount);
		waitForNewClients();
	}

	private void waitForNewClients() {
		while (mRunning) {
			Socket socket;
			try {
				socket = mServerSocket.accept();

				if (mClientManagement.isPlayerCountReached()) {
					sendFullMessage(socket);
				} else {
					addClient(socket);
				}

				startGameIfAllClientsConnected();
			} catch (IOException e) {
			}
		}
	}

	private void sendInitialGameStateMessage() {
		GameStateMessage gameStateMessage = new GameStateMessage(
				mGame.getGameState());
		mClientManagement.sendMessage(gameStateMessage);
	}

	private void addClient(Socket socket) throws IOException {
		ClientConnection clientConnection = new ClientConnection(socket, this);

		mClientManagement.addClient(clientConnection);
		new Thread(clientConnection).start();
	}

	private void sendFullMessage(Socket socket) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(new FullMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGameIfAllClientsConnected() {
		if (mClientManagement.isPlayerCountReached()) {
			// mLog.log(MessageTexts.getMessage("M019"));
			mGame = new Game(mClientManagement.getPlayerNames(),
					mClientManagement);
			sendInitialGameStateMessage();
		}
	}

	public void shutdown() {
		try {
			mRunning = false;
			mServerSocket.close();
			mClientManagement.shutdown();
			// mLog.log(MessageTexts.getMessage("M020"));
		} catch (IOException e) {
		}
	}
}