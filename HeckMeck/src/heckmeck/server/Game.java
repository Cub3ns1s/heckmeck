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
		
		for (Iterator iterator = playerList.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			
			mPlayers.add(new PlayerState(name));
		}
		
	}

	public Game move() {
		return null;
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
