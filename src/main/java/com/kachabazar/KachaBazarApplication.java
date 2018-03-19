package com.kachabazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.kachabazar")
public class KachaBazarApplication {

	public static void main(String[] args) {
		SpringApplication.run(KachaBazarApplication.class, args);
	}
}
