package com.epam.trainingdurationmicroservice.service;

import com.epam.trainingdurationmicroservice.dto.TrainingDurationCountDto;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerService {

	private final TrainerRepository trainerRepository;
	private final ObjectMapper objectMapper;
	private static final int START_YEAR = 2000;
	private static final int END_YEAR = 2100;
	private static final String ACTION_TYPE_ADD = "ADD";
	private static final String ACTION_TYPE_DELETE = "DELETE";

	public void modifyTrainerWorkingTimeDuration(TrainingDurationCountDto request) throws IOException {
		try {
			String username = request.getTrainerUsername();
			String firstName = request.getTrainerFirstName();
			String lastName = request.getTrainerLastName();
			Boolean isActive = request.getIsActive();
			String actionType = request.getActionType();
			Integer duration = request.getTrainingDuration();
			Date trainingDate = request.getTrainingDate();

			Trainer trainer = trainerRepository.findByUsername(username).orElse(null);
			if (trainer == null) {
				trainer = new Trainer();
				trainer.setFirstName(firstName);
				trainer.setLastName(lastName);
				trainer.setUsername(username);
				trainer.setIsActive(isActive);
				trainer.setTrainingDurationPerMonth(null);
			}

			LocalDate localTrainingDate = trainingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			String trainingDuration = trainer.getTrainingDurationPerMonth();
			Map<String, Map<String, Integer>> durationMap;
			if (trainingDuration != null) {
				durationMap = objectMapper.readValue(trainingDuration, new TypeReference<Map<String, Map<String, Integer>>>() {
				});
			} else {
				durationMap = new HashMap<>();
				for (int year = START_YEAR; year <= END_YEAR; year++) {
					durationMap.put(String.valueOf(year), null);
				}
			}
			Map<String, Integer> yearMap = durationMap.get(String.valueOf(localTrainingDate.getYear()));
			if (yearMap == null) {
				yearMap = new HashMap<>();
				for (int month = 1; month < 13; month++) {
					yearMap.put(String.valueOf(month), 0);
				}
			}

			trainer.setTrainingDurationPerMonth(objectMapper.writeValueAsString(modifyDuration(durationMap, yearMap, actionType, duration, localTrainingDate)));
			trainerRepository.save(trainer);
		} catch (Exception e) {
			log.error("Service: Error while modifying trainer's working time duration: {}", e.getMessage());
			throw e;
		}

	}

	private Map<String, Map<String, Integer>> modifyDuration(Map<String, Map<String, Integer>> durationMap, Map<String, Integer> yearMap, String actionType, Integer duration, LocalDate localTrainingDate) {
		Integer modifiedDuration;

		switch (actionType) {
			case ACTION_TYPE_ADD:
				modifiedDuration = duration + yearMap.get(String.valueOf(localTrainingDate.getMonthValue()));
				log.info(modifiedDuration + " minutes");
				yearMap.put(String.valueOf(localTrainingDate.getMonthValue()), modifiedDuration);
				break;
			case ACTION_TYPE_DELETE:
				modifiedDuration = yearMap.get(String.valueOf(localTrainingDate.getMonthValue())) - duration;
				if (modifiedDuration < 0) {
					modifiedDuration = 0;
				}
				yearMap.put(String.valueOf(localTrainingDate.getMonthValue()), modifiedDuration);
				log.info(modifiedDuration + " minutes");
				break;
			default:
				throw new UnsupportedOperationException("Unsupported action type");
		}
		durationMap.put(String.valueOf(localTrainingDate.getYear()), yearMap);
		return durationMap;
	}

}
