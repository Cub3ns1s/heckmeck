package heckmeck.server;

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
	 * checks if all players are connected and have sent their logon messages
	 */
	public boolean isPlayerCountReached() {

		return (mClients.size() == mPlayerCount);
	}

	/**
	 * sends message
	 * 
	 * @param message
	 */
	public void sendMessage(ServerMessage message) {
		for (Iterator<ClientConnection> iterator = mClients.iterator(); iterator
				.hasNext();) {
			ClientConnection client = iterator.next();
			client.sendMessage(message);
		}
	}

	/**
	 * gets names of connected players
	 * 
	 * @return
	 */
	public List<String> getPlayerNames() {

		ArrayList<String> clientList = new ArrayList<String>();

		for (Iterator<ClientConnection> iterator = mClients.iterator(); iterator
				.hasNext();) {
			ClientConnection client = iterator.next();

			clientList.add(client.getName());
		}
		return clientList;
	}

	/**
	 * shuts Client Connections down
	 */
	public void shutdown() {
		for (Iterator<ClientConnection> iterator = mClients.iterator(); iterator
				.hasNext();) {
			ClientConnection clientConnection = iterator.next();
			clientConnection.shutdown();
		}
	}

}
