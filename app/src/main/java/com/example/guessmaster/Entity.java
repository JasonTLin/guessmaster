package com.example.guessmaster;

/**
 * Abstract Entity class represents any real-life entity that can be associated
 * with a name and date.
 * 
 * @author Jason T. Lin 20071773
 * @version 8.0
 */
public abstract class Entity {
	private String name;
	private Date born;
	private double difficulty;



	public Entity() {
		name = "";
		born = null; /* Not a real date */
		difficulty = 0;
	}

	public Entity(String newName, Date newBorn, double newDifficulty) {
		setEntity(newName, newBorn, newDifficulty);
	}

	public void setEntity(String newName, Date newBorn, double newDifficulty) {
		name = newName;
		born = newBorn;
		difficulty = newDifficulty;
	}

	public Entity(Entity e) {
		/* Provides a deep copy of an entity*/
		this.name = e.name;
		this.born = new Date(e.born);
		this.difficulty = e.difficulty;
	}

	public Date getDate() {
		return new Date(born);
	}

	public String getName() {
		return name; /* Strings are immutable */
	}

	public double getDifficulty() {
		return difficulty; /* No privacy leak since doubles are primitive */
	}

	public String toString() {
		return "Name: " + name + "\nBorn at: " + born.toString();
	}

	public int getAwardedTicketNumber() {
		return (int) (difficulty * 100);
	}

	public abstract String entityType();

	public abstract Entity clone();

	public String welcomeMessage() {
		return "Welcome! Let's start the game! " + this.entityType();
	}

	public String closingMessage() {
		return "Congratulations! The detailed information of the entity you guess is: \n" + this.toString();
	}

	public boolean equals(Entity entity1, Entity entity2) {
		/*
		 * Check for equality between two entity objects by checking the name and birth
		 * date.
		 */
		return ((entity1.name.equals(entity2.name)) && (entity1.born.equals(entity2.born)));
	}
}
