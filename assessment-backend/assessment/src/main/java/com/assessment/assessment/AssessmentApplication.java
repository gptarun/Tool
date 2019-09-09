package com.assessment.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssessmentApplication {
	
	
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name","application");
		SpringApplication.run(AssessmentApplication.class, args);
	}

}

