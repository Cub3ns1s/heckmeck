package heckmeck.server;

import java.util.List;

public interface GameStateInterface {

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
}
