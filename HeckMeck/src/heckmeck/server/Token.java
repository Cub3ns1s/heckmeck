package heckmeck.server;

import java.io.Serializable;

public class Token implements Comparable<Token>, Serializable {

	// Attributes
	private static final long serialVersionUID = 4613913251186739276L;
	private int mValue;
	private int mWorms;
	private boolean mActive;

	// Constructor
	public Token(int m_value) {
		this.mValue = m_value;
		this.mActive = true;
		this.mWorms = getWormsForValue(m_value);
	}

	private int getWormsForValue(int value) {
		if (value <= 24) {
			return 1;
		}
		if (value <= 28) {
			return 2;
		}
		if (value <= 32) {
			return 3;
		} else {
			return 4;
		}
	}

	public boolean isActive() {
		return mActive;
	}

	public int getValue() {
		return mValue;
	}

	public int getWorms() {
		return mWorms;
	}

	public void deactivate() {
		mActive = false;
	}

	public int compareTo(Token other) {
		Token otherToken = other;
		return mValue - otherToken.mValue;
	}

	@Override
	public String toString() {
		return mValue + " - " + mWorms + "\n";
	}
}