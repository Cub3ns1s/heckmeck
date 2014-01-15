package heckmeck.server;

import heckmeck.client.Client;

import java.util.*;

public class ClientManagement {

	// Attributes
	private List<ClientConnection> mClients;
	private int mPlayerCount;

	
	// Constructor
	public ClientManagement(int playerCount) {
		this.mClients = new ArrayList<ClientConnection>();
		this.mPlayerCount = playerCount;
	}

	/**
	 * Adds client to list
	 * 
	 * @param clientListener
	 */
	public void addClient(ClientConnection clientConnection) {
		this.mClients.add(clientConnection);

	}

	/**
	 * checks number of players
	 */
	public void checkPlayerCount() {
		if (mClients.size() == mPlayerCount) {
			// Game.start();
		}
	}

	public void sendMessage( ServerMessage message){
		for (Iterator iterator = mClients.iterator(); iterator.hasNext();) {
			ClientConnection client = (ClientConnection) iterator.next();
			client.sendMessage( message );
		}
	}
	
}
