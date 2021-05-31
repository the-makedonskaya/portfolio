package ru.makedonskaya.smartnotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SmartNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartNotesApplication.class, args);
	}

}
