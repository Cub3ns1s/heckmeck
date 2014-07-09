package heckmeck.server;

import java.io.Serializable;
import java.util.List;

public interface GameState extends Serializable {

	public Grill getGrill();

	public List<PlayerState> getPlayerStates();

	public PlayerState getCurrentPlayer();
}