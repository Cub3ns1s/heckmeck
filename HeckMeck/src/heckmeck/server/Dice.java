package heckmeck.server;

import java.io.Serializable;

public class Dice implements Serializable, Comparable<Dice> {

	// Attributes
	private static final long serialVersionUID = 7745295383873720798L;
	private int mValue;
	private String mLabel;

	// Constructor
	public Dice() {
		dice();
	}

	/**
	 * returns value of dice
	 * 
	 * @return
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
		mLabel = Integer.toString(mValue);
		
		if (mValue == 6) {
			mValue = 5;
			mLabel = "W";
		}
		System.out.println(mLabel);
	}

	@Override
	public int compareTo(Dice otherDice) {
		return getLabel().compareTo(otherDice.getLabel());
	}

	public String getLabel() {
		return mLabel;
	}

	@Override
	public String toString() {
		return "Dice [mValue=" + mValue + "]";
	}

}
