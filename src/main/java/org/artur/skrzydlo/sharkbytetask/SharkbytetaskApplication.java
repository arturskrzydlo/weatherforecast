package org.artur.skrzydlo.sharkbytetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SharkbytetaskApplication {

	public static void main(String[] args) {

		SpringApplication.run(SharkbytetaskApplication.class, args);

	}
}
