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

	/**
	 * returns amount of worms for value
	 * 
	 * @param value
	 * @return 
	 */
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

	/**
	 * returns if token is active or not
	 * 
	 * @return 
	 */
	public boolean isActive() {
		return mActive;
	}

	/**
	 * returns value of token
	 * 
	 * @return 
	 */
	public int getValue() {
		return mValue;
	}

	/**
	 * returns amount of worms of token
	 * 
	 * @return 
	 */
	public int getWorms() {
		return mWorms;
	}

	/**
	 * deactivates token
	 */
	public void deactivate() {
		mActive = false;
	}

	/**
	 * compares token to another token
	 * 
	 * @return 
	 */
	public int compareTo(Token other) {
		Token otherToken = other;
		return mValue - otherToken.mValue;
	}

	@Override
	public String toString() {
		return mValue + " - " + mWorms + "\n";
	}
	
	
}
