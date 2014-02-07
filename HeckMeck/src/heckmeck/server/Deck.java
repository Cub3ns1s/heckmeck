package heckmeck.server;

import heckmeck.exceptions.NoTokenFoundException;

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
	 * @return 
	 * @throws NoTokenFoundException 
	 */
	public Token getTopToken() throws NoTokenFoundException {
		if(mToken.size() != 0) {
		return mToken.get((mToken.size()-1));
		}
		else {
			throw new NoTokenFoundException();
		}
	}

	/**
	 * returns size of deck
	 * 
	 * @return
	 */
	public int getSize() {
		return mToken.size();
	}

	/**
	 * adds token to deck
	 * 
	 * @param token
	 */
	public void addToken(Token token) {
		mToken.add(token);
	}

	/**
	 * removes token from deck
	 */
	public void removeTopToken() {
		mToken.remove(mToken.size());
	}

	/**
	 * @return
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
