package Heckmeck;

import java.io.IOException;
import java.net.*;
import java.lang.Thread;

public class Server implements Runnable{

	//Attributes
	private int mPlayerCount;
	private ServerSocket mServerSocket;
	private Thread mThread;
	
	//Konstruktor
	private Server( int playerCount ) {
		
		System.out.println( "Starte Server für " + playerCount + " Spieler");
//		Warten auf Anmeldung
		for (int i = 0; i < playerCount; i++) {
			
		try {
		mServerSocket = new ServerSocket();
		mThread = new Thread(this);
		mThread.start();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		}

//		Spiel starten
//		Game game = new Game( this );
	}
	
	@Override
	public void run() {

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