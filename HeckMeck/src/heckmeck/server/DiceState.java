package heckmeck.server;

import heckmeck.exceptions.*;

import java.io.Serializable;
import java.util.*;

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
		try {
			dice();
		} catch (MisthrowException e) {
			e.printStackTrace();
		}
	}

	public void dice() throws MisthrowThrowException {
		for (int i = 0; i < mUnfixedDices.size(); i++) {
			mUnfixedDices.get(i).dice();
		}

		if (isMisthrow()) {
			throw new MisthrowThrowException();
		}

		sort();
	}

	public void fixValue(String label) throws AlreadyFixedException,
			ValueNotFoundException {
		boolean valueFound = false;

		if (isValueFixed(label)) {
			throw new AlreadyFixedException();
		}

		for (Iterator<Dice> iterator = mUnfixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();

			if (dice.getLabel().equals(label)) {
				mFixedDices.add(dice);
				valueFound = true;
			}
		}

		mUnfixedDices.removeAll(mFixedDices);
		sort();

		if (!valueFound) {
			throw new ValueNotFoundException();
		}
	}

	private void sort() {
		Collections.sort(mFixedDices);
		Collections.sort(mUnfixedDices);
	}

	public void clear() {
		mUnfixedDices.addAll(mFixedDices);
		mFixedDices.clear();

		assert (mUnfixedDices.size() == 8);
		try {
			dice();
		} catch (MisthrowThrowException e) {
			e.printStackTrace();
		}
	}

	public int getDicedValue() {
		int result = 0;

		for (Iterator<Dice> iterator = mFixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();

			result = dice.getValue();
		}

		return result;
	}

	private boolean validateValueFixed() {
		for (int i = 0; i < mUnfixedDices.size(); i++) {

			if (!isValueFixed(mUnfixedDices.get(i).getLabel())) {
				return false;
			}
		}

		return true;
	}

	private boolean isValueFixed(String label) {
		for (int i = 0; i < mFixedDices.size(); i++) {
			Dice dice = mFixedDices.get(i);

			if (dice.getLabel().equals(label)) {
				return true;
			}
		}

		return false;
	}

	private boolean isMisthrow() {
		return validateValueFixed();
	}

	public List<Dice> getUnfixedDices() {
		return mUnfixedDices;
	}

	public void setUnfixedDices(List<Dice> unfixedDices) {
		this.mUnfixedDices = unfixedDices;
	}

	public List<Dice> getFixedDices() {
		return mFixedDices;
	}

	public void setFixedDices(List<Dice> fixedDices) {
		this.mFixedDices = fixedDices;
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		List<Dice> fixedDices = getFixedDices();
		List<Dice> unfixedDices = getUnfixedDices();

		sB.append("Fixed: ");
		for (Iterator<Dice> iterator = fixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();
			sB.append(dice.getLabel() + " ");
		}
		sB.append("\nUnfixed: ");
		for (Iterator<Dice> iterator = unfixedDices.iterator(); iterator
				.hasNext();) {
			Dice dice = iterator.next();
			sB.append(dice.getLabel() + " ");
		}
		return sB.toString();
	}
}