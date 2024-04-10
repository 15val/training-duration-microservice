package com.epam.trainingdurationmicroservice.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "trainer")
public class Trainer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", columnDefinition = "VARCHAR(10000)")
	private String 	firstName;

	@Column(name = "last_name", columnDefinition = "VARCHAR(10000)")
	private String  lastName;

	@Column(name = "username", columnDefinition = "VARCHAR(10000)")
	private String username;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "training_duration_per_month", columnDefinition = "VARCHAR(100000)")
	private String trainingDurationPerMonth; //JSON value of map

}
