package heckmeck.server;

import heckmeck.exceptions.WrongPlayerCount;

import java.io.IOException;
import java.net.*;
import java.lang.Thread;
import java.net.Socket;

public class Server {

	// Attributes
	private ServerSocket mServerSocket;
	private ClientManagement mClientManagement;
	private Game mGame;

	// Constructor
	private Server(int playerCount) {
		mClientManagement = new ClientManagement(playerCount);
		try {
			mServerSocket = new ServerSocket(23534);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			checkPlayerCount(playerCount);
		} catch (WrongPlayerCount e1) {
			return;
		}

		startThreads(playerCount);

		mClientManagement.sendMessage(new WelcomeMessage("Hallo"));

		log("Starte Spiel");
		mGame = new Game(mClientManagement.getPlayerNames());
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

		new Server(playerCount);
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
	 * 
	 * @param playerCount
	 * @throws WrongPlayerCount
	 */
	public void checkPlayerCount(int playerCount) throws WrongPlayerCount {
		if (playerCount == 0) {
			log("Mindestens zwei Spieler benötigt!");
			throw new WrongPlayerCount();
		}
		if (playerCount == 1) {
			log("Mindestens zwei Spieler benötigt!");
			throw new WrongPlayerCount();
		}

		if (playerCount > 7) {
			log("Maximal sieben Spieler erlaubt!");
			throw new WrongPlayerCount();
		}
	}

	/**
	 * waits for players and starts threads
	 * 
	 * @param playerCount
	 */
	public void startThreads(int playerCount) {
		log("Starte Server für " + playerCount + " Spieler");
		for (int i = 0; i < playerCount; i++) {
			Socket socket;
			try {
				socket = mServerSocket.accept();
				ClientConnection clientConnection = new ClientConnection(
						socket, this);

				mClientManagement.addClient(clientConnection);
				new Thread(clientConnection).start();
				log("Thread mit ClientConnection erstellt und gestartet");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}