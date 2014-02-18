package heckmeck.server;

import java.io.Serializable;
import java.util.List;

public interface GameState extends Serializable{

	/**
	 * abstract method for getting the grill
	 * 
	 * @return
	 */
	public Grill getGrill();

	/**
	 * abstract method for getting the player states
	 * 
	 * @return
	 */
	public List<PlayerState> getPlayerStates();
	
	public PlayerState getCurrentPlayer();
}
