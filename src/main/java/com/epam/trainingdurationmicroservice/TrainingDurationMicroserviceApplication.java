package com.epam.trainingdurationmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TrainingDurationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingDurationMicroserviceApplication.class, args);
	}

}
