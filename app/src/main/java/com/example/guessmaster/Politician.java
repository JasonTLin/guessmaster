package com.example.guessmaster;

/**
 * Person class which extends the politician class.
 * Introduces instance variable party of type String.
 * 
 * @author Jason T. Lin 20071773
 * @version 8.0
 */
public class Politician extends Person {
	private String party;

	public Politician() {
		super();
		party = null;
	}

	public Politician(String newName, Date newBorn, String newGender, String newParty, double newDifficulty) {
		super(newName, newBorn, newGender, newDifficulty);
		setParty(newParty);
	}

	public void setParty(String newParty) {
		party = newParty; /* Strings are immutable */
	}

	public Politician(Politician p) {
		super(p);
		setParty(p.party);
	}

	public Politician clone() {
		return new Politician(this);
	}

	public String toString() {
		return (super.toString() + "\nParty: " + party);
	}

	public String entityType() {
		return "This is a politician!";
	}
}
