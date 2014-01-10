package heckmeck;

import java.io.Serializable;
import java.util.*;

public class Deck implements Serializable {

	// Attributes
	private static final long serialVersionUID = -7606947981178065654L;
	private List<Token> mToken;

	
	// Constructor
	public Deck() {
		this.mToken = new ArrayList<Token>();
	}

	/**
	 * returns token on top of deck
	 * 
	 * @return Token
	 */
	public Token getTopToken() {
		return this.mToken.get(this.mToken.size());
	}

	/**
	 * returns size of deck
	 * 
	 * @return int
	 */
	public int getSize() {
		return this.mToken.size();
	}

	/**
	 * adds token to deck
	 * 
	 * @param token
	 */
	public void addToken(Token token) {
		this.mToken.add(token);
	}

	/**
	 * removes token from deck
	 */
	public void removeTopToken() {
		this.mToken.remove(this.mToken.size());
	}

	/**
	 * @return int
	 */
	public int getWorms() {
		int result = 0;

		for (Iterator<Token> iterator = mToken.iterator(); iterator.hasNext();) {
			Token token = iterator.next();
			result += token.getWorms();
		}
		return result;
	}
}
