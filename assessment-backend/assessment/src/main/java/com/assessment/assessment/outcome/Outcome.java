package com.assessment.assessment.outcome;

public class Outcome { //Collection of learning actions for a competency
	private long area;
	private long category;
	private long competency;
	private String categoryName;
	private String competencyName;
	private String [] outcomes;
	private int type = 0;
	
	public Outcome(long area, long category, long competency, String[] outcomes) {
		super();
		this.area = area;
		this.category = category;
		this.competency = competency;
		this.outcomes = outcomes;
	}
	
	public Outcome(long area, long category, long competency) {
		super();
		this.area = area;
		this.category = category;
		this.competency = competency;
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
	public String[] getOutcomes() {
		return outcomes;
	}
	public void setOutcomes(String[] outcomes) {
		this.outcomes = outcomes;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCompetencyName() {
		return competencyName;
	}

	public void setCompetencyName(String competencyName) {
		this.competencyName = competencyName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
