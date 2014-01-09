package Heckmeck;

import java.util.*;

public class ClientManagement {
	
	//Attributes
	private List<Client> mClients;
	private int mPlayerCount;

	//Constructor
	public ClientManagement(int playerCount) {
		this.mClients = new ArrayList<Client>();
		this.mPlayerCount = playerCount;
	}
	
	/**
	 * Adds client to list
	 * @param client
	 */
	public void addClient(Client client) {
		this.mClients.add(client);
		
		checkPlayerCount();
	}

	/**
	 * checks number of players
	 */
	public void checkPlayerCount() {
		if (mClients.size() == mPlayerCount) {
			//Game.start();
		}	
	}
	
	
	
	
}
