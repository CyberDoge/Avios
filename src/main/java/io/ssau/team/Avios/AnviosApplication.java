package io.ssau.team.Avios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AnviosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnviosApplication.class, args);
	}

}
