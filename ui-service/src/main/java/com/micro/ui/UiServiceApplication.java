package com.micro.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class UiServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UiServiceApplication.class, args);
		log.info("START UI-MICROSERVICE");
	}

}
