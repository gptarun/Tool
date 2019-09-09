package com.assessment.assessment.outcome;

public class IndividualOutcome { //An individual learning action for the user. Tied to the results for the maturity level that the user obtained.
	private long catID;
	private long compID;
	private String catName;
	private String compName;
	private String outcomeText;
	private int level;
	
	public IndividualOutcome(long catID, long compID, String catName, String compName, int level) {
		super();
		this.catID = catID;
		this.compID = compID;
		this.catName = catName;
		this.compName = compName;
		this.level = level;
	}

	public IndividualOutcome(long catID, long compID, String catName, String compName) {
		super();
		this.catID = catID;
		this.compID = compID;
		this.catName = catName;
		this.compName = compName;
	}

	public long getCatID() {
		return catID;
	}

	public void setCatID(long catID) {
		this.catID = catID;
	}

	public long getCompID() {
		return compID;
	}

	public void setCompID(long compID) {
		this.compID = compID;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getOutcomeText() {
		return outcomeText;
	}

	public void setOutcomeText(String outcomeText) {
		this.outcomeText = outcomeText;
	}
	
	
}
