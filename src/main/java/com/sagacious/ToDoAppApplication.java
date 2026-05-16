package com.sagacious;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*") // allow all origins
@SpringBootApplication(scanBasePackages = "com.sagacious")
public class ToDoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoAppApplication.class, args);
	}

}
