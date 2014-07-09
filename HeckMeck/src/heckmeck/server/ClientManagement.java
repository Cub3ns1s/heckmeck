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

	public void sendMessage(ServerMessage message) {
		for (Iterator<ClientConnection> iterator = mClients.iterator(); iterator
				.hasNext();) {
			ClientConnection client = iterator.next();
			client.sendMessage(message);
		}
	}

	public void addClient(ClientConnection clientConnection) {
		this.mClients.add(clientConnection);
	}

	public boolean isPlayerCountReached() {
		return (mClients.size() == mPlayerCount);
	}

	public List<String> getPlayerNames() {

		ArrayList<String> clientList = new ArrayList<String>();

		for (Iterator<ClientConnection> iterator = mClients.iterator(); iterator
				.hasNext();) {
			ClientConnection client = iterator.next();

			clientList.add(client.getName());
		}
		return clientList;
	}

	public void shutdown() {
		for (Iterator<ClientConnection> iterator = mClients.iterator(); iterator
				.hasNext();) {
			ClientConnection clientConnection = iterator.next();
			clientConnection.shutdown();
		}
	}
}