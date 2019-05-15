package com.example.guessmaster;

import android.app.AlertDialog;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.content.DialogInterface;
import android.util.Log;

import android.os.Bundle;

public class GuessMaster extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guess_activity);
		guessButton = (Button) findViewById(R.id.btnGuess);
		btnclearContent = (Button) findViewById(R.id.btnClear);
		userIn = (EditText) findViewById(R.id.guessInput);
		ticketsum = (TextView) findViewById(R.id.ticket);
		entity_Image = (ImageView) findViewById(R.id.entityImage);
		entityName = (TextView) findViewById(R.id.entityName);

		final TextWatcher validDate = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				user_input = userIn.getText().toString().trim();
				boolean valid = false;

				if(user_input.length() == 2){
					Integer month = Integer.parseInt(user_input.substring(0, 2));
					if(month <= 12 && month >=1){
						user_input += "/";
						userIn.removeTextChangedListener(this);
						userIn.setText(user_input);
						userIn.setSelection(user_input.length());
						userIn.addTextChangedListener(this);
					} else {
						userIn.setError("Please enter a valid date (MM/DD/YYYY)");
					}
				}
				user_input = user_input.trim();
				if(user_input.length() == 5){
					Integer day = Integer.parseInt(user_input.substring(3, 5));
					if(day <=31 && day >=1){
						user_input += "/";
						userIn.removeTextChangedListener(this);
						userIn.setText(user_input);
						userIn.setSelection(user_input.length());
						userIn.addTextChangedListener(this);
					} else {
					userIn.setError("Please enter a valid date (MM/DD/YYYY)");
					}
				}
				if(user_input.length() == 10){
					Integer year = Integer.parseInt(user_input.substring(6, 10));

					if(year <= 9999 && year >= 1000){
						valid = true;
					}
				}

				guessButton.setEnabled(valid);

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};

		userIn.addTextChangedListener(validDate);


		Politician trudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);
		Singer dion = new Singer("Celine Dion", new Date("March", 30, 1968), "Female", "La voix du bon Dieu",
				new Date("March", 30, 1981), 0.5);
		Person myCreator = new Person("myCreator", new Date("September", 1, 2000), "Female", 1);
		Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);

		addEntity(trudeau);
		addEntity(myCreator);
		addEntity(dion);
		addEntity(usa);

		currentEntity = entities[genRandomEntityInd()];
		entityName.setText(currentEntity.getName());
		ImageSetter(currentEntity);
		welcomeToGame(currentEntity);

		//OnClick Lister action for clear button
		btnclearContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeEntity();
			}
		});

		//OnClick Listener action for submit button
		guessButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playGame(currentEntity);
			}
		});

	}

	public int numberOfCandidateEntities;
	private int totalTickets;
	private int entityTickets;

	private Entity[] entities;
	private TextView entityName;
	private TextView ticketsum;
	private EditText userIn;
	private Button guessButton;
	private Button btnclearContent;
	private String user_input;
	private ImageView entity_Image;
	private String answer;
	private Entity currentEntity;



	/**
	 * This method extends array of entities. Copies the elements of previous array
	 * into a new array that is 1 element larger. Entity parameter is inserted at
	 * last index and numberOfCandidateEntities is incremented. Make the previous
	 * array point to the new array.
	 *
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		if (entities != null) {
			numberOfCandidateEntities = entities.length;
		}

		Entity[] tempEntities = new Entity[numberOfCandidateEntities + 1];

		for (int i = 0; i < numberOfCandidateEntities; i++) {
			tempEntities[i] = entities[i].clone(); /* Create deep copy using clone method using late binding */
		}

		tempEntities[numberOfCandidateEntities] = entity;

		numberOfCandidateEntities++;

		entities = tempEntities;
	}

	/**
	 * This method takes a date from a player and checks if's the same as the
	 * entitie's. Game will keep playing until correct date is inputed. Gives hints
	 * to guess later or earlier based on input.
	 *
	 * @param entity
	 * @see Date
	 */
	public void playGame(Entity entity) {

		ImageSetter(entity);

		entityName.setText(entity.getName());

			answer = userIn.getText().toString();

			answer = answer.replace("\n", "").replace("\r", "");

			Date guessDate = new Date(answer);

			if (guessDate.equals(entity.getDate())) {

				totalTickets += entity.getAwardedTicketNumber();
				String tickets = Integer.toString(totalTickets);
				ticketsum.setText("Tickets: "+ tickets);

				entityTickets = entity.getAwardedTicketNumber();

				AlertDialog.Builder winAlert = new AlertDialog.Builder(GuessMaster.this);
				winAlert.setTitle("You won");
				winAlert.setMessage("BINGO! \n" + entity.closingMessage());
				winAlert.setCancelable(false);
				winAlert.setNegativeButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getBaseContext(), "You won " + entityTickets + " tickets in this round", Toast.LENGTH_SHORT).show();
						ContinueGame();
					}
				});

				AlertDialog win = winAlert.create();
				win.show();

			} else if (guessDate.precedes(entity.getDate())) {

				AlertDialog.Builder lateAlert = new AlertDialog.Builder(GuessMaster.this);
				lateAlert.setTitle("Incorrect");
				lateAlert.setMessage("Incorrect. Try a later date.");
				lateAlert.setCancelable(false);
				lateAlert.setNegativeButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
					}
				});

				AlertDialog late = lateAlert.create();
				late.show();
				userIn.getText().clear();

			} else {
				AlertDialog.Builder earlyAlert = new AlertDialog.Builder(GuessMaster.this);
				earlyAlert.setTitle("Incorrect");
				earlyAlert.setMessage("Incorrect. Try a earlier date.");
				earlyAlert.setCancelable(false);
				earlyAlert.setNegativeButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_SHORT).show();
					}
				});

				AlertDialog early = earlyAlert.create();
				early.show();
				userIn.getText().clear();
			}
		}

	public void playGame(int entityInd) {
		playGame(entities[entityInd]);
	}

	public void playGame() {
		playGame(genRandomEntityInd());
	}

	public void changeEntity() {
		userIn.getText().clear();

		currentEntity = entities[genRandomEntityInd()];

		ImageSetter(currentEntity);

		entityName.setText(currentEntity.getName());
	}

	public void ContinueGame(){
		currentEntity = entities[genRandomEntityInd()];

		ImageSetter(currentEntity);

		entityName.setText(currentEntity.getName());

		userIn.getText().clear();
	}

	public void ImageSetter(Entity entity){

		if(entity.getName().equals("Justin Trudeau")){
			entity_Image.setImageResource(R.drawable.justint);
		} else if(entity.getName().equals("Celine Dion")){
			entity_Image.setImageResource(R.drawable.celidion);
		} else if(entity.getName().equals("United States")){
			entity_Image.setImageResource(R.drawable.usaflag);
		} else if(entity.getName().equals("myCreator")){
			entity_Image.setImageResource(R.drawable.kid);
		}

	}


	private int genRandomEntityInd() {
		/*
		 * Generates a pseudo-random number between 0 and 3.
		 */
		return (int) (Math.random() * 4);
	}

	private void welcomeToGame(Entity entity){
		AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);
		welcomealert.setTitle("GuessMaster Game V3");
		welcomealert.setMessage(entity.welcomeMessage());
		welcomealert.setCancelable(false);
		welcomealert.setNegativeButton("START GAME", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getBaseContext(), "Game is Starting... Enjoy", Toast.LENGTH_SHORT).show();
			}
		});

			AlertDialog dialog = welcomealert.create();
			dialog.show();

	}

	public static void main(String[] args) {

		Politician trudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);
		Singer dion = new Singer("Celine Dion", new Date("March", 30, 1968), "Female", "La voix du bon Dieu",
				new Date("March", 30, 1981), 0.5);
		Person myCreator = new Person("myCreator", new Date("September", 1, 2000), "Female", 1);
		Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);

		GuessMaster newgame = new GuessMaster();
		newgame.addEntity(trudeau);
		newgame.addEntity(myCreator);
		newgame.addEntity(dion);
		newgame.addEntity(usa);

		newgame.playGame();
	}
}
