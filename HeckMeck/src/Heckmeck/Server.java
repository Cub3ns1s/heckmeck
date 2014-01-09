package Heckmeck;

public class Server  {

	//Attributes
	private int mPlayerCount;
	
	
	//Konstruktor
	private Server( int playerCount ){
		
		System.out.println( "Starte Server für " + playerCount + " Spieler");
//		Warten auf Anmeldung
		
//		Spiel starten
//		Game game = new Game( this );
	}
	
	
	public static void main(String[] args){
		
		int playerCount = 2;
				
		if (args.length > 0){
			try{
			playerCount = new Integer(args[0]);
			}	catch (NumberFormatException nfe){
				
			}
		}

		new Server( playerCount );
				
	}	
}