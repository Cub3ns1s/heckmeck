package Heckmeck;

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

	
	// Constructor
	private Server(int playerCount) {
		try {
			mServerSocket = new ServerSocket(23534);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Starte Server für " + playerCount + " Spieler");
		// Warten auf Anmeldung
		for (int i = 0; i < playerCount; i++) {
			Socket socket = new Socket();
			try {
				socket = mServerSocket.accept();
				new Thread(new PlayerListener(socket));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Spiel starten
		// Game game = new Game( this );
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