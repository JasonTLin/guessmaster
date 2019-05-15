package com.example.guessmaster;

/**
 * Country class which extends the entity class.
 * Introduces instance variable capital of type String.
 * 
 * @author Jason T. Lin 20071773
 * @version 8.0
 */
public class Country extends Entity {

	private String capital;

	public Country() {
		super();
		capital = null;
	}

	public Country(String newName, Date newBorn, String newCapital, double newDifficulty) {
		super(newName, newBorn, newDifficulty);
		setCapital(newCapital);
	}

	public void setCapital(String newCapital) {
		capital = newCapital; /* Strings are immutable */
	}

	public Country(Country c) {
		super(c); /*Invoke copy constructor of entity*/
		this.setCapital(c.capital);
	}

	public Country clone() {
		return new Country(this);
	}

	public String toString() {
		return (super.toString() + "\nCapital: " + capital);
	}

	public String entityType() {
		return "This entity is a country!";
	}
}
