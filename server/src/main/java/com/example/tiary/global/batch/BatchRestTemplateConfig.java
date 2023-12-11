package com.example.tiary.global.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BatchRestTemplateConfig {

	@Bean
	public RestTemplate batchRestTemplate(){
		return new RestTemplate();
	}
}
