package heckmeck.server;

import heckmeck.exceptions.NoTokenFoundException;

import java.util.*;

public class Grill {

	// Attributes
	private SortedSet<Token> mTokens;

	// Constructor
	public Grill() {
		init();
	}

	/**
	 * initializes list with 16 tokens
	 */
	private void init() {
		mTokens = new TreeSet<Token>();
		for (int i = 21; i < 37; i++) {
			this.mTokens.add(new Token(i));
		}
	}

	/**
	 * removes token from grill
	 * 
	 * @param tokenNr
	 * @return 
	 * @throws NoTokenFoundException
	 */
	public Token remove(int tokenNr) throws NoTokenFoundException {
		for (Iterator<Token> iterator = mTokens.iterator(); iterator.hasNext();) {
			Token token = iterator.next();

			if (tokenNr == token.getValue()) {
				mTokens.remove(token);
				return token;
			}
		}

		throw new NoTokenFoundException();
	}

	/**
	 * adds token to grill after misthrow
	 * 
	 * @param token
	 */
	public void failure(Token token) {
		if (token != null) {
			mTokens.add(token);
		}
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("GRILL \n");
		
		for (Iterator<Token> iterator = mTokens.iterator(); iterator.hasNext();) {
			Token token = iterator.next();
			sB.append(token.toString());
		}
		sB.append("\n********************\n");
		return sB.toString();
	}

	

}
