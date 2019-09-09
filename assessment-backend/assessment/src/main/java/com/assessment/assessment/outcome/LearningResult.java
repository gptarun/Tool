package com.assessment.assessment.outcome;

import java.util.List;

import com.assessment.assessment.session.Session;

public class LearningResult { //All of the learning actions and models that the user obtained

	private List<IndividualOutcome> outcomes;
	private List<IndividualOutcome> models;
	private List<Session> sessionHistory;
	public LearningResult() {
		
	}

	public LearningResult(List<IndividualOutcome> outcomes, List<IndividualOutcome> models) {
		super();
		this.outcomes = outcomes;
		this.models = models;
	}

	public List<IndividualOutcome> getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(List<IndividualOutcome> outcomes) {
		this.outcomes = outcomes;
	}

	public List<IndividualOutcome> getModels() {
		return models;
	}

	public void setModels(List<IndividualOutcome> models) {
		this.models = models;
	}
	
	public String toString() {
		for(int i = 0; i < outcomes.size(); i++) {
			System.out.println(outcomes.get(i));
		}
		return "end";
	}

	public List<Session> getSessionHistory() {
		return sessionHistory;
	}

	public void setSessionHistory(List<Session> sessionHistory) {
		this.sessionHistory = sessionHistory;
	}
	
}
