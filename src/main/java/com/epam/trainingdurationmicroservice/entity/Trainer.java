package com.epam.trainingdurationmicroservice.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "trainer")
public class Trainer {

	@Id
	private String id;

	private String username;

	private String 	firstName;

	private String  lastName;

	private Boolean isActive;

	private String trainingDurationPerMonth;

}
