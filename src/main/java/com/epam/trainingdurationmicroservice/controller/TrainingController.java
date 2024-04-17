package com.epam.trainingdurationmicroservice.controller;

import com.epam.trainingdurationmicroservice.dto.TrainingDurationCountDto;
import com.epam.trainingdurationmicroservice.service.TrainerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/training")
@Slf4j
public class TrainingController {

	private final TrainerService trainerService;

	@PostMapping("/modifyWorkingTime")
	public ResponseEntity<HttpStatus> modifyTrainingWorkingTimeDuration(@RequestBody TrainingDurationCountDto request) {
		log.info("Modifying method started");
		try {
			trainerService.modifyTrainerWorkingTimeDuration(request);
			log.info("Trainer working time duration successfully changed");
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Controller: Error while creating training: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}