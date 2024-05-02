package com.epam.trainingdurationmicroservice.controller;

import com.epam.trainingdurationmicroservice.dto.GetTrainingDurationMapDto;
import com.epam.trainingdurationmicroservice.dto.UsernameDto;
import com.epam.trainingdurationmicroservice.exception.TrainerIsMissingException;
import com.epam.trainingdurationmicroservice.exception.TrainingDurationMapIsNullException;
import com.epam.trainingdurationmicroservice.service.TrainerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/trainer")
@Slf4j
public class TrainerController {

	private final TrainerService trainerService;

	@GetMapping("/getWorkingTime")
	public ResponseEntity<GetTrainingDurationMapDto> modifyTrainingWorkingTimeDuration(@RequestBody UsernameDto request) {
		log.info("Modifying method started");
		try {
			GetTrainingDurationMapDto response = trainerService.getTrainingDurationMapDto(request);
			log.info("Trainer working time duration successfully retrieved");
			return ResponseEntity.ok(response);
		}
		catch (TrainerIsMissingException e){
			log.error("Controller: Error while retrieving training duration map: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		}
		catch (TrainingDurationMapIsNullException e){
			log.error("Controller: Trainer exists but his training duration map is null: {}", e.getMessage());
			return ResponseEntity.notFound().build();
		}
		catch (Exception e) {
			log.error("Controller: Error while retrieving training duration map: {}", e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}
