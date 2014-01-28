package heckmeck.server;

import heckmeck.exceptions.NoTokenFound;

import java.util.*;

public class Grill {

	// Attributes
	private SortedSet<Token> mt_token;

	// Constructor
	public Grill() {
		init();
	}

	/**
	 * initializes list with 16 tokens
	 */
	private void init() {
		mt_token = new TreeSet<Token>();
		for (int i = 21; i < 37; i++) {
			this.mt_token.add(new Token(i));
		}
	}

	/**
	 * removes token from grill
	 * 
	 * @param tokenNr
	 * @return 
	 * @throws NoTokenFound
	 */
	public Token remove(int tokenNr) throws NoTokenFound {
		for (Iterator<Token> iterator = mt_token.iterator(); iterator.hasNext();) {
			Token token = iterator.next();

			if (tokenNr == token.getValue()) {
				mt_token.remove(token);
				return token;
			}
		}

		throw new NoTokenFound();
	}

	/**
	 * adds token to grill after misthrow
	 * 
	 * @param token
	 */
	public void failure(Token token) {
		if (token != null) {
			mt_token.add(token);
		}
	}

}
