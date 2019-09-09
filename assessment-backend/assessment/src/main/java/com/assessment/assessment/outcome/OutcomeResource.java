package com.assessment.assessment.outcome;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assessment.assessment.session.Session;
import com.assessment.assessment.session.SessionDAO;

@CrossOrigin(origins="${origin}")
@RestController
public class OutcomeResource {
	@Autowired
	private OutcomeService outService;
	@Autowired
	private SessionDAO dao;
	
	@GetMapping("/{session}/outcome")
	public LearningResult getAllOutcomes(@PathVariable String session) throws Exception{ //gets all Outcomes AND Models
		List<Outcome> outcomes = outService.allOutcomes();
		List<Outcome> models = outService.allModels();
		int sessionID = dao.getIdByUrl(session);
		outService.addUpTotals(sessionID, session);
		List<IndividualOutcome> learning = determineOutcomes(outcomes, sessionID);
		List<IndividualOutcome> model = determineOutcomes(models, sessionID);
		LearningResult lr = new LearningResult();
		lr.setOutcomes(learning);
		lr.setModels(model);
		return lr;
	}
	
	@GetMapping("/{session}/outcome/history")
	public LearningResult getCombinedOutcomes(@PathVariable String session) throws Exception{ //gets all Outcomes AND Models
		List<Session> sessions = dao.sessionHistory(session);
		List<Outcome> outcomes = outService.allOutcomes();
		//List<Outcome> models = outService.allModels();
		int sessionID = (int) sessions.get(sessions.size()-1).getDbId();
		outService.addUpTotals(sessionID, sessions, false);
		List<IndividualOutcome> learning = determineOutcomes(outcomes, sessionID);
		//List<IndividualOutcome> model = determineOutcomes(models, sessionID);
		LearningResult lr = new LearningResult();
		lr.setOutcomes(learning);
		lr.setSessionHistory(sessions);
		outService.clearCategory(sessionID);
		//lr.setModels(model);
		return lr;
	}
	
	public List<IndividualOutcome> determineOutcomes(List<Outcome> outcomes, int sessionID) throws Exception{
		List<IndividualOutcome> outStr = new ArrayList();
		for(Outcome o:outcomes) {
			IndividualOutcome outItem = outService.determineOutcome(o.getArea(), o.getCategory(), o.getCompetency(), o.getOutcomes(), sessionID, o.getType(), o);
			outStr.add(outItem);
		}
		return outStr;
	}
	@GetMapping("/outcome")
	public List<Outcome> getAllOutcomesForAllLevels() throws Exception{
		return outService.allOutcomes();
	}
	
//	@PutMapping("/{session}/outcome")  //I have to check if this is used at all
//	public String sendEmail(@PathVariable String session, @RequestBody String outLink) {
//		return outLink;
//	}
	
	@PutMapping("/outcome") 
	public OutcomeLoader addOutcome(@RequestBody OutcomeLoader outLoader) throws Exception{
		return outService.newOutcome(outLoader);
	}
	
	@PutMapping("/model")
	public OutcomeLoader addModel(@RequestBody OutcomeLoader outLoader) throws Exception{
		return outService.newModel(outLoader);
	}
	
	@PutMapping("/outcome/update")
	public OutcomeLoader updateOutcome(@RequestBody OutcomeLoader outLoader) throws Exception{
		return outService.updateOutcome(outLoader);
	}
}
