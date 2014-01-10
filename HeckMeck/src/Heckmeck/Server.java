package Heckmeck;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.lang.Thread;
import java.net.Socket;
public class Server{

	//Attributes
	private int mPlayerCount;
	private ServerSocket mServerSocket;
	private Thread mThread;
	
	//Konstruktor
	private Server( int playerCount ) {
		try {
		mServerSocket = new ServerSocket( 23534 );
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println( "Starte Server für " + playerCount + " Spieler");
//		Warten auf Anmeldung
		for (int i = 0; i < playerCount; i++) {
			mThread.start();
		}
		

//		Spiel starten
//		Game game = new Game( this );
	}
	
	@Override
	public void run() {
		
		Socket socket = new Socket();
		ObjectInputStream ois = null;
		
		System.out.println("Thread läuft");
		
		try {
			socket = mServerSocket.accept();
		} catch (IOException e) {

			e.printStackTrace();
		} 

		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		
		
        
        try {
			ClientMessage message = (ClientMessage)ois.readObject();
		} catch (ClassNotFoundException | IOException e) {

			e.printStackTrace();
		}
        
		
	}	
	
	
	public static void main(String[] args){
		
		int playerCount = 2;
				
		if (args.length > 0){
			try{
			playerCount = new Integer(args[0]);
			}
			catch (NumberFormatException nfe){};
		}
		
			new Server( playerCount );
		}
				
	}