package heckmeck.server;

import heckmeck.exceptions.PlayerCountOne;
import heckmeck.exceptions.PlayerCountZero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.lang.Thread;
import java.net.Socket;

public class Server {

	// Attributes
	private ServerSocket mServerSocket;
	private Thread mThread;
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

		// Warten auf Anmeldung
		if (playerCount == 0) {
			log("Mindestens zwei Spieler benötigt!");
			return;
		}
		if (playerCount == 1) {
			log("Mindestens zwei Spieler benötigt!");
			return;
		}

		if (playerCount > 7) {
			log("");
		}
		log("Starte Server für " + playerCount + " Spieler");
		for (int i = 0; i < playerCount; i++) {
			Socket socket;
			try {
				socket = mServerSocket.accept();
				ClientConnection clientListener = new ClientConnection(socket,
						this);

				mClientManagement.addClient(clientListener);
				new Thread(clientListener).start();
				log("Thread mit ClientListener erstellt und gestartet");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		mClientManagement.sendMessage(new WelcomeMessage("Hallo"));

		// Spiel starten
		mGame = new Game(mClientManagement.getPlayerNames());
	}
	
	// Log Message
	public static void log(String message) {
		System.out.println(message);
	}

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
	
	// Move
	public void move(Decision decision) {
		
		GameStateMessage gameStateMessage = new GameStateMessage(mGame.move(decision));
		mClientManagement.sendMessage(gameStateMessage);
	}
}