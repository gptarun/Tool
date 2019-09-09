package com.assessment.assessment.question;

public class Question { //The questions being asked on the assessment. Contains information about the possible answer choices, and the value of said choices
	private long id;
	private String description;
	private String [] choices;
	private int [] levels;
	private float weight;
	private long area;
	private long category;
	private long competency;
	private String catString;
	private String compString;
	
	public Question() {
		
	}
	public Question(long id, String description, String[] choices, int[] levels, float weight, long area, long category,
			long competency) {
		super();
		this.id = id;
		this.description = description;
		this.choices = choices;
		this.levels = levels;
		this.weight = weight;
		this.area = area;
		this.category = category;
		this.competency = competency;
	}
	
	public String getCatString() {
		return catString;
	}
	public void setCatString(String catString) {
		this.catString = catString;
	}
	public String getCompString() {
		return compString;
	}
	public void setCompString(String compString) {
		this.compString = compString;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getChoices() {
		return choices;
	}
	public void setChoices(String[] choices) {
		this.choices = choices;
	}
	public int[] getLevels() {
		return levels;
	}
	public void setLevels(int[] levels) {
		this.levels = levels;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
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
	
	
}
