package heckmeck.server;

import java.util.List;

public interface GameState {

	public Grill getGrill();
	
	public List<PlayerState> getPlayerStates();
}
