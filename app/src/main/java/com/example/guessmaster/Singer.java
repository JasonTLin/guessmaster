package com.example.guessmaster;

/**
 * Person class which extends the singer class.
 * Introduces instance variable debutAlbum of type String.
 * Introduces instance variable debutAlbumReleaseDate of type Date.
 * 
 * @author Jason T. Lin 20071773
 * @version 8.0
 */
public class Singer extends Person {
	private String debutAlbum;
	Date debutAlbumReleaseDate;

	public Singer() {
		super();
		debutAlbum = null;
		debutAlbumReleaseDate = null;
	}

	public Singer(String newName, Date newBorn, String newGender, String newDebutAlbum, Date newDebutAlbumReleaseDate,
			double newDifficulty) {

		super(newName, newBorn, newGender, newDifficulty);
		debutAlbum = newDebutAlbum;
		debutAlbumReleaseDate = newDebutAlbumReleaseDate;
	}

	public Singer(Singer s) {
		super(s);
		debutAlbum = s.debutAlbum;
		debutAlbumReleaseDate = new Date(s.debutAlbumReleaseDate);
	}

	public Singer clone() {
		return new Singer(this);
	}

	public String toString() {
		return (super.toString() + "\nDebut Album: " + debutAlbum + "\nRelease Date: "
				+ debutAlbumReleaseDate.toString());
	}

	public String entityType() {
		return "This is a singer!";
	}
}
