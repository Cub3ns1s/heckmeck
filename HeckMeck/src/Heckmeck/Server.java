package Heckmeck;

public class Server  {

	private int mPlayerCount;
	
	public static void main(String[] args){
		
		int playerCount = 2;
				
		if (args.length > 0){
			try{
			playerCount = new Integer(args[0]);
			}	catch (NumberFormatException nfe){
				
			}
		}

		new Server( playerCount );
		
		
//		String abc = "ABC";
//		String abd = "ABC";
//		
//		if (abc == abd){
//		System.out.println( "Gleich" );
//		}
//		else{
//		System.out.println( "Ungleich" );
//		}
//		char a = 'A' ;
		
	}

	private Server( int playerCount ){
		
		System.out.println( "Starte Server für " + playerCount + " Spieler");
//		Warten auf Anmeldung
		
//		Spiel starten
//		Game game = new Game( this );
	}
	
}