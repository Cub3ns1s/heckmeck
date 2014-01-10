package Heckmeck;

import java.io.Serializable;

public class Dice implements Serializable {

	// Attributes
	private static final long serialVersionUID = 7745295383873720798L;
	private int mValue;

	
	// Constructor
	public Dice() {
		dice();
	}

	/**
	 * returns value of dice
	 * 
	 * @return int
	 */
	public int getValue() {
		return mValue;
	}

	/**
	 * sets value of dice
	 * 
	 * @param mValue
	 */
	public void setValue(int mValue) {
		this.mValue = mValue;
	}

	/**
	 * dices dice and saves value
	 */
	public void dice() {
		mValue = (int) (Math.random() * 6 + 1);
	}

}
