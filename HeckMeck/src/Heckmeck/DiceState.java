package Heckmeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Heckmeck.Exceptions.AlreadyFixed;
import Heckmeck.Exceptions.Misthrow;
import Heckmeck.Exceptions.ValueNotFound;

public class DiceState implements Serializable {

	// Attributes
	private static final long serialVersionUID = -8819803397763601581L;
	private List<Dice> mUnfixedDices;
	private List<Dice> mFixedDices;

	
	// Constructor
	public DiceState() {

		mUnfixedDices = new ArrayList<Dice>();
		mFixedDices = new ArrayList<Dice>();

		for (int i = 0; i < 8; i++) {
			mUnfixedDices.add(new Dice());
		}
	}

	/**
	 * gets list of unfixed dices
	 * 
	 * @return
	 */
	public List<Dice> getUnfixedDices() {
		return mUnfixedDices;
	}

	/**
	 * sets list of unfixed dices
	 * 
	 * @param unfixedDices
	 */
	public void setmUnfixedDices(List<Dice> unfixedDices) {
		this.mUnfixedDices = unfixedDices;
	}

	/**
	 * gets list of fixed dices
	 * 
	 * @return
	 */
	public List<Dice> getFixedDices() {
		return mFixedDices;
	}

	/**
	 * set list of fixed dices
	 * 
	 * @param fixedDices
	 */
	public void setmFixedDices(List<Dice> fixedDices) {
		this.mFixedDices = fixedDices;
	}

	/**
	 * returns sum of diced dices
	 * 
	 * @return int
	 */
	public int getDicedValue() {

		int result = 0;

		for (Iterator<Dice> iterator = mFixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();

			result = dice.getValue();
		}

		return result;
	}

	/**
	 * dices all unfixed dices
	 * 
	 * @throws Misthrow
	 */
	public void dice() throws Misthrow {

		for (Iterator<Dice> iterator = mUnfixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();

			dice.dice();

			if (isMisthrow()) {
				throw new Misthrow();
			}
		}
	}

	/**
	 * checks whether a misthrow occured
	 * 
	 * @return boolean
	 */
	private boolean isMisthrow() {

		for (Iterator<Dice> iterator = mUnfixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();

			if (!isValueFixed(dice.getValue())) {
				return false;
			}
		}

		return true;
	}

	/**
	 * checks whether value is fixed
	 * 
	 * @param value
	 * @return boolean
	 */
	private boolean isValueFixed(int value) {
		for (Iterator<Dice> iterator = mFixedDices.iterator(); iterator
				.hasNext();) {

			Dice dice = iterator.next();

			if (dice.getValue() == value) {
				return true;
			}
		}

		return false;
	}

	/**
	 * fixes all dices with given value
	 * 
	 * @param value
	 * @throws AlreadyFixed
	 * @throws ValueNotFound
	 */
	public void fixValue(int value) throws AlreadyFixed, ValueNotFound {
		boolean valueFound = false;

		if (isValueFixed(value)) {
			throw new AlreadyFixed();
		}

		for (Iterator<Dice> iterator = mUnfixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();

			if (dice.getValue() == value) {

				mFixedDices.add(dice);
				mUnfixedDices.remove(dice);

				valueFound = true;
			}
		}

		if (!valueFound) {
			throw new ValueNotFound();
		}
	}

	/**
	 * clears list of fixed dices and resets dicestate
	 */
	public void clear() {

		mUnfixedDices.addAll(mFixedDices);
		mFixedDices.clear();

		assert (mUnfixedDices.size() == 8);
	}
}
