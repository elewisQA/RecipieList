package com.qa.recipelists;

//---[ Imports ]---
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//---[ SpringBoot Runner ]---
@SpringBootApplication
@EnableWebMvc
public class RecipieListsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipieListsApplication.class, args);
	}

}
