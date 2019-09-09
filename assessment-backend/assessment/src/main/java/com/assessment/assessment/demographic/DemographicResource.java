package com.assessment.assessment.demographic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="${origin}")
@RestController
public class DemographicResource {

	@Autowired
	private DemographicService demoService;
	
	@PutMapping("/team")
	public long importTeam(@RequestBody Demographic team) throws Exception {
		return demoService.importTeam(team);
	}
	
	
	@GetMapping("/portfolio")
	public List<Portfolio> getPortfolios() throws Exception {
		return demoService.getPortfolios();
	}
}
