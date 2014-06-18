package heckmeck.server;

import heckmeck.exceptions.WrongPlayerCountException;

import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Server implements Runnable{

	// Attributes
	private ServerSocket mServerSocket;
	private ClientManagement mClientManagement;
	private Game mGame;
	private int mPlayerCount;
	private static final int MINPLAYER = 2;
	private static final int MAXPLAYER = 4;
	private SysoLog mLog;
	private boolean mRunning = true;
	
	// Constructor
	public Server(int playerCount) {
		mPlayerCount = playerCount;
		mLog = new SysoLog();
		mClientManagement = new ClientManagement(mPlayerCount);
		
		new MessageTexts("English");
		
	}

	/**
	 * initializes gameStateMessage and sends message
	 * 
	 * @param decision
	 */
	public void move(DecisionMessage decision, String playerName) {
		GameStateMessage gameStateMessage = new GameStateMessage(
				mGame.move(decision, playerName));

		mClientManagement.sendMessage(gameStateMessage);
	}

	/**
	 * checks player count
	 * 
	 * @param playerCount
	 * 
	 * @throws WrongPlayerCountException
	 */
	public void checkPlayerCount(int playerCount)
			throws WrongPlayerCountException {
		if (playerCount < MINPLAYER) {
			mLog.log(MessageTexts.getMessage("M016"));
			throw new WrongPlayerCountException();
		}

		if (playerCount > MAXPLAYER) {
			mLog.log(MessageTexts.getMessage("M017"));
			throw new WrongPlayerCountException();
		}
	}

	/**
	 * waits for players and starts threads
	 * 
	 * @param playerCount
	 */
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

		mLog.log(MessageTexts.getMessage("M018") + mPlayerCount);
		waitForNewClients();

	}

	/**
	 * waits for new clients to start game
	 */
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
				mLog.log(e);
			}
			
		}
	}

	/**
	 * sends initial GameStateMessage
	 */
	private void sendInitialGameStateMessage() {
		GameStateMessage gameStateMessage = new GameStateMessage(
				mGame.getGameState());
		mClientManagement.sendMessage(gameStateMessage);
	}

	/**
	 * adds Client Connection to Client Management
	 * 
	 * @param socket
	 * @throws IOException
	 */
	private void addClient(Socket socket) throws IOException {
		ClientConnection clientConnection = new ClientConnection(socket, this);

		mClientManagement.addClient(clientConnection);
		new Thread(clientConnection).start();
	}

	/**
	 * sends Message when server is full
	 * 
	 * @param socket
	 */
	private void sendFullMessage(Socket socket) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(new FullMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if all the clients are conntected and starts the game if they are
	 */
	public void startGameIfAllClientsConnected() {

		if (mClientManagement.isPlayerCountReached()) {
			mLog.log(MessageTexts.getMessage("M019"));
			mGame = new Game(mClientManagement.getPlayerNames(), mClientManagement);
			sendInitialGameStateMessage();
		}

	}

	/**
	 * shuts server down
	 */
	public void shutdown() {
		try {
			mRunning = false;
			mServerSocket.close();
			mClientManagement.shutdown();
			mLog.log(MessageTexts.getMessage("M020"));
		} catch (IOException e) {
			mLog.log(e);
		}
	}

}