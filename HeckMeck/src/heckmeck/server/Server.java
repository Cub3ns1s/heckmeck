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
	private int mPlayerCount;
	private ServerSocket mServerSocket;
	private Thread mThread;
	private ClientManagement mClientManagement;

	// Constructor
	private Server(int playerCount) {
		mClientManagement = new ClientManagement( playerCount);
		try {
			mServerSocket = new ServerSocket(23534);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Starte Server für " + playerCount + " Spieler");
		
		// Warten auf Anmeldung
		if (mPlayerCount == 0) {
			try {
				throw new PlayerCountZero();
			} catch (PlayerCountZero e) {
				e.printStackTrace();
			}}
		if(mPlayerCount == 1) {
				try {
					throw new PlayerCountOne();
				} catch (PlayerCountOne e) {
					e.printStackTrace();
				}}
		else {
		for (int i = 0; i < playerCount; i++) {
			Socket socket;
			try {
				socket = mServerSocket.accept();
				ClientConnection clientListener = new ClientConnection(socket, this);
				
				mClientManagement.addClient( clientListener );
				new Thread(clientListener).start();
				System.out.println("Thread mit ClientListener erstellt und gestartet");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}}
		
		mClientManagement.sendMessage(new WelcomeMessage( "Hallo"));

		// Spiel starten
		 Game game = new Game(mClientManagement.getPlayerNames());
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

}