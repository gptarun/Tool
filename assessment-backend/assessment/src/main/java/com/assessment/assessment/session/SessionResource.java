package com.assessment.assessment.session;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins="${origin}")
@RestController
public class SessionResource {
	
	
	@Autowired
	private SessionService sessionService;
	
	
	@GetMapping("/{session}/session")
	public Session getSession(@PathVariable String session) {
		return sessionService.retrieveSessionById(session);
	}
	
	@GetMapping("/{session}/history")
	public List<Session> sessionHistory(@PathVariable String session) throws Exception{
		return sessionService.sessionHistory(session);
	}
	
	@PutMapping("/{sessionId}/cat/{id}")
	public ResponseEntity<String> setCategory(@PathVariable long sessionId, @PathVariable long id, @RequestBody String team) throws Exception{
		System.out.println(team);
		String str = sessionService.setCategory(sessionId, id, team);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
	@PutMapping("/session/create/{portfolio}")
	public ResponseEntity<Session> createSession(@PathVariable int portfolio, @RequestBody Session session) throws Exception{
		System.out.println(session.getEmail());
		
		Session s = sessionService.newSession(session, portfolio);
		return new ResponseEntity<Session>(s, HttpStatus.OK); 
	}
	
	@PutMapping("/{session}/close")
	public ResponseEntity<Date> closeSession(@PathVariable String session) throws Exception{
		sessionService.closeSession(session);
		return new ResponseEntity<Date>(new Date(), HttpStatus.OK);
		
	}
	@PutMapping("/session/demographic/{id}") //Not used right now
	public Session teamDemographics(@PathVariable long id, @RequestBody Session session) throws Exception{
		return sessionService.teamDemo(session, id);
	}
	
	@PutMapping("/session/demographic/works/{id}") //Not used right now
	public List<String> teamWorks(@PathVariable long id, @RequestBody List<String> works) throws Exception{
		return sessionService.teamWorks(works, id);
	}
	
}
