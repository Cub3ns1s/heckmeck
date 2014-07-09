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

	public void dice() {
		mValue = (int) (Math.random() * 6 + 1);
		mLabel = Integer.toString(mValue);

		if (mValue == 6) {
			mValue = 5;
			mLabel = "W";
		}
		System.out.println(mLabel);
	}

	public String getLabel() {
		return mLabel;
	}

	public int getValue() {
		return mValue;
	}

	public void setValue(int mValue) {
		this.mValue = mValue;
	}

	@Override
	public int compareTo(Dice otherDice) {
		return getLabel().compareTo(otherDice.getLabel());
	}

	@Override
	public String toString() {
		return "Dice [mValue=" + mValue + "]";
	}
}