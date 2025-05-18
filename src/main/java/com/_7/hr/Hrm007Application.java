package com._7.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
@EnableNeo4jAuditing
public class Hrm007Application {
	public static void main(String[] args) {
		SpringApplication.run(Hrm007Application.class, args);
	}

}
