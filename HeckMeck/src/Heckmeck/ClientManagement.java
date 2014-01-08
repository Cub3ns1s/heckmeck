package Heckmeck;

import java.util.*;

public class ClientManagement {
	
	private List<Client> mClients;
	private int mPlayerCount;

	public ClientManagement(int playerCount) {
		this.mClients = new ArrayList<Client>();
		this.mPlayerCount = playerCount;
	}
	
	public void addClient(Client client) {
		this.mClients.add(client);
		
		checkPlayerCount();
	}

	public void checkPlayerCount() {
		if (mClients.size() == mPlayerCount) {
			//Game.start();
		}	
	}
	
	
	
	
}
