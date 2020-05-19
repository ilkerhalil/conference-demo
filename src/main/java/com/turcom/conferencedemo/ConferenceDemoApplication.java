package com.turcom.conferencedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
public class ConferenceDemoApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		ElasticApmAttacher.attach();
		SpringApplication.run(ConferenceDemoApplication.class, args);

	}

}
