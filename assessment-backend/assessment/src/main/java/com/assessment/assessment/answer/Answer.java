package com.assessment.assessment.answer;

public class Answer {  //Stores responses made by user (does not include the string value of their response, only the ID that the response is mapped to in the database)

	private long id; //same value as question id
	private int choice;
	private long area;
	private long category;
	private long competency;
	private float weight;
	private int sessionID;
	
	protected Answer() {
		
	}

	public Answer(long id, int choice) {
		super();
		this.id = id;
		this.choice = choice;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public long getArea() {
		return area;
	}

	public void setArea(long area) {
		this.area = area;
	}

	public long getCategory() {
		return category;
	}

	public void setCategory(long category) {
		this.category = category;
	}

	public long getCompetency() {
		return competency;
	}

	public void setCompetency(long competency) {
		this.competency = competency;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	
}
