package com.example.guessmaster;

/**
 * Person class which extends the entity class.
 * Introduces instance variable gender of type String.
 * 
 * @author Jason T. Lin 20071773
 * @version 8.0
 */
public class Person extends Entity {

	private String gender;

	public Person() {
		super();
		gender = null;
	}

	public Person(String newName, Date newBorn, String newGender, double newDifficulty) {
		super(newName, newBorn, newDifficulty);
		setGender(newGender);
	}

	public void setGender(String newGender) {
		gender = newGender;
	}

	public Person(Person p) {
		super(p);
		setGender(p.gender);
	}

	public Person clone() {
		return new Person(this);
	}

	public String toString() {
		return (super.toString() + "\nGender: " + gender);
	}

	public String entityType() {
		return "This entity is a person!";
	}

}
