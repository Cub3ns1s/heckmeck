package heckmeck.unittests;

import static org.junit.Assert.*;

import java.util.Iterator;

import heckmeck.exceptions.MisthrowException;
import heckmeck.server.Dice;
import heckmeck.server.DiceState;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UTServer_DiceStateSort {

	private DiceState diceState;
	
	@Before
	public void setUp() throws Exception {
		diceState = new DiceState();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int value_tmp = 0;
		
		try {
			diceState.dice();
		} catch (MisthrowException e) {
			e.printStackTrace();
		}
		
		for (Iterator<Dice> iterator = diceState.getUnfixedDices().iterator(); iterator.hasNext();) {
			Dice dice = iterator.next();
			
			if (dice.getValue() < value_tmp) {
				fail("not sorted" + iterator.toString());
			}
			value_tmp = dice.getValue();
		}
		System.out.println(diceState.getUnfixedDices().toString());
	}

}
