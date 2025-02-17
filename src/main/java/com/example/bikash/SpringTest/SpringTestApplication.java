package com.example.bikash.SpringTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringTestApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringTestApplication.class, args);
	}


}
