package com.assessment.assessment.outcome;

public class Model extends Outcome { //I don't know specifically what this is. Its a description of the user's maturity level?

	public Model(long area, long category, long competency) {
		super(area, category, competency);
		setType(1);
	}

	public Model(long area, long category, long competency, String[] outcomes) {
		super(area, category, competency, outcomes);
		setType(1);
	}
	
}
