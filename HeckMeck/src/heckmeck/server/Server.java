package heckmeck.server;

import heckmeck.exceptions.WrongPlayerCountException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.lang.Thread;
import java.net.Socket;

public class Server {

	// Attributes
	private ServerSocket mServerSocket;
	private ClientManagement mClientManagement;
	private Game mGame;
	private int mPlayerCount;
	private static final int MINPLAYER = 2;
	private static final int MAXPLAYER = 7;

	// Constructor
	public Server(int playerCount) {
		mPlayerCount = playerCount;
		mClientManagement = new ClientManagement(mPlayerCount);
		try {
			mServerSocket = new ServerSocket(23534);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main method - gets player count and starts server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		int playerCount = 2;

		if (args.length > 0) {
			try {
				playerCount = new Integer(args[0]);
			} catch (NumberFormatException nfe) {
			}
			;
		}
		
		try {
			checkPlayerCount(playerCount);
		} catch (WrongPlayerCountException e1) {
			return;
		}
		
		Server server = new Server(playerCount);
		server.startThreads(playerCount);
	}

	/**
	 * prints message
	 * 
	 * @param message
	 */
	public static void log(String message) {
		System.out.println(message);
	}

	/**
	 * initializes gameStateMessage and sends message
	 * 
	 * @param decision
	 */
	public void move(Decision decision) {

		GameStateMessage gameStateMessage = new GameStateMessage(
				mGame.move(decision));
		mClientManagement.sendMessage(gameStateMessage);
	}

	/**
	 * checks player count
	 * @param playerCount 
	 * 
	 * @throws WrongPlayerCountException
	 */
	public static void checkPlayerCount(int playerCount) throws WrongPlayerCountException {
		if (playerCount < MINPLAYER) {
			log("Mindestens zwei Spieler benötigt!");
			throw new WrongPlayerCountException();
		}

		if (playerCount > MAXPLAYER) {
			log("Maximal sieben Spieler erlaubt!");
			throw new WrongPlayerCountException();
		}
	}

	/**
	 * waits for players and starts threads
	 * 
	 * @param playerCount
	 */
	public void startThreads(int playerCount) {
		log("Starte Server für " + playerCount + " Spieler");
		while (true) {

			Socket socket;
			try {
				socket = mServerSocket.accept();

				if (mClientManagement.isPlayerCountReached()) {
					sendFullMessage(socket);
				} else {
					addClient(socket);
					if (mClientManagement.isPlayerCountReached()) {
						mClientManagement.sendMessage(new WelcomeMessage(
								"Hallo"));

						log("Starte Spiel");
						mGame = new Game(mClientManagement.getPlayerNames());
					}
				}

			} catch (IOException e) {
				log(e);
			}

		}

	}

	private void log(IOException e) {
		for (int i = 0; i < e.getStackTrace().length; i++) {
			StackTraceElement ste = e.getStackTrace()[i];
			log(ste.toString());
		}
	}

	private void addClient(Socket socket) throws IOException {
		ClientConnection clientConnection = new ClientConnection(socket, this);

		mClientManagement.addClient(clientConnection);
		new Thread(clientConnection).start();
		log("Thread mit ClientConnection erstellt und gestartet");
	}

	private void sendFullMessage(Socket socket) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());
			oos.writeObject(new FullMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}