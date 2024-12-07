package com.juandlr.springsecurityproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Security and JWT REST API Documentation",
				description = "Spring security project REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Juan de la Rosa",
						email = "apexjohn67@gmail.com",
						url = "https://github.com/johndlr"
				)
		)
)
public class SpringSecurityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityProjectApplication.class, args);
	}

}
