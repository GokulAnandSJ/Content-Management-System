package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDoc {

		Contact contact() {
		return new Contact().name("Gokul").url("www.Gokul.com").email("gokulanand0110@gmail.com");
	}
	
	
	@Bean
	Info info() {
		return new Info().title("Content Management System").description("Restful API with basic CRUD operation.").version("v1");
	}
	
	@Bean
	OpenAPI openApi() {
		return new OpenAPI().info(info().contact(contact()));
	}
}
