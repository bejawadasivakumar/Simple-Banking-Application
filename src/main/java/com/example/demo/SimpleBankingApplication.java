package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Simple Banking Application",
				description = "Simple Banking Application using Java, Spring Boot, MySQL, RESTAPI",
				contact = @Contact(
						name = "shiva",
						email = "shiva@example.com"
						)
				)
		)

public class SimpleBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankingApplication.class, args);
	}

}
