package com.epam.trainingdurationmicroservice.service;

import com.epam.trainingdurationmicroservice.dto.TrainingMicroserviceDto;
import com.epam.trainingdurationmicroservice.entity.Trainer;
import com.epam.trainingdurationmicroservice.repository.TrainerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerService {

	private final TrainerRepository trainerRepository;
	private final ObjectMapper objectMapper;
	private static final int START_YEAR = 2000;
	private static final int END_YEAR = 2100;
	@Transactional
	public void modifyTrainerWorkingTimeDuration(TrainingMicroserviceDto request) throws ParseException, IOException {
		try {
			String username = request.getTrainerUsername();
			String firstName = request.getTrainerFirstName();
			String lastName = request.getTrainerLastName();
			Boolean isActive = request.getIsActive();
			String actionType = request.getActionType();
			Integer duration = request.getTrainingDuration();
			Date trainingDate = request.getTrainingDate();

			Trainer trainer = trainerRepository.findByUsername(username).orElse(null);
			if(trainer == null){
				trainer = new Trainer();
				trainer.setFirstName(firstName);
				trainer.setLastName(lastName);
				trainer.setUsername(username);
				trainer.setIsActive(isActive);
				trainer.setTrainingDurationPerMonth(null);
				trainerRepository.save(trainer);
			}

			Map<String, Map<String, Integer>> durationMap = objectMapper.readValue(trainer.getTrainingDurationPerMonth(), new TypeReference<Map<String, Map<String, Integer>>>() {});
			if(durationMap == null){
				durationMap = new HashMap<>();
				for(int year = START_YEAR; year <= END_YEAR; year++){
					durationMap.put(String.valueOf(year), null);
				}
			}
			Map<String, Integer> yearMap = durationMap.get(String.valueOf(trainingDate.getYear()));
			if (yearMap == null) {
				yearMap = new HashMap<>();
				for (int month = 0; month < 12; month++) {
					yearMap.put(String.valueOf(month), 0);
				}
			}

			if (actionType.equalsIgnoreCase("ADD")) {
				Integer modifiedDuration = duration + yearMap.get(String.valueOf(trainingDate.getMonth()));
				log.info(modifiedDuration + " minutes");
				yearMap.put(String.valueOf(trainingDate.getMonth()), modifiedDuration);
				durationMap.put(String.valueOf(trainingDate.getYear()), yearMap);
			} else if (actionType.equalsIgnoreCase("DELETE")) {
				Integer modifiedDuration = yearMap.get(String.valueOf(trainingDate.getMonth())) - duration;
				if (modifiedDuration < 0) {
					modifiedDuration = 0;
				}
				yearMap.put(String.valueOf(trainingDate.getMonth()), modifiedDuration);
				log.info(modifiedDuration + " minutes");
				durationMap.put(String.valueOf(trainingDate.getYear()), yearMap);
			} else {
				throw new UnsupportedOperationException("Unsupported action type");
			}

			trainer.setFirstName(firstName);
			trainer.setLastName(lastName);
			trainer.setUsername(username);
			trainer.setIsActive(isActive);
			trainer.setTrainingDurationPerMonth(objectMapper.writeValueAsString(durationMap));
			trainerRepository.save(trainer);
		} catch (Exception e) {
			log.error("Service: Error while modifying trainer's working time duration: {}", e.getMessage());
			throw e;
		}

	}

}
