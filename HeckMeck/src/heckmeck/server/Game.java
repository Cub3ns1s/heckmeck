package heckmeck.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game implements GameState{
	
	//Attributes
	private Grill mGrill;
	private List<PlayerState> mPlayers;

	
	//Constructor
	public Game(List<String> playerList) {
		
		initPlayerStates(playerList);
		
		mGrill = new Grill();
	}
	
	private void initPlayerStates(List<String> playerList) {
		
		mPlayers = new ArrayList<PlayerState>();
		
		for (Iterator<String> iterator = playerList.iterator(); iterator.hasNext();) {
			String name = iterator.next();
			
			mPlayers.add(new PlayerState(name));
		}
		
	}

	public GameState move(Decision decision) {
		return this;
	}
	
	public GameState getGameState() {
		return this;
	}
	
	@Override
	public Grill getGrill() {
		return mGrill;
	}

	@Override
	public List<PlayerState> getPlayerStates() {
		return mPlayers;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(args[0]);
	}

}
