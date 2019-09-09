package com.assessment.assessment.outcome;

public class OutcomeLoader { //Modified outcome object designed for uploading/updating outcomes in DB
	
	private String catName;
	private String compName;
	private String [] outcomes;
	public OutcomeLoader(String catName, String compName, String[] outcomes) {
		super();
		this.catName = catName;
		this.compName = compName;
		this.outcomes = outcomes;
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
	public String[] getOutcomes() {
		return outcomes;
	}
	public void setOutcomes(String[] outcomes) {
		this.outcomes = outcomes;
	}
	
	
}
