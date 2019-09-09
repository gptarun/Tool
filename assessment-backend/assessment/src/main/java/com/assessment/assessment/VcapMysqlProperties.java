package com.assessment.assessment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("vcap.services.online-assessment-sso-test-db")
public class VcapMysqlProperties {
	public String jdbcUrl;

	
	
	
	
}
