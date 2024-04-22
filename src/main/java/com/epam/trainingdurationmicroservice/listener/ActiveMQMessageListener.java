package com.epam.trainingdurationmicroservice.listener;

import com.epam.trainingdurationmicroservice.dto.TrainingDurationCountDto;
import com.epam.trainingdurationmicroservice.service.TrainerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class ActiveMQMessageListener {

	private TrainerService trainerService;
	private JmsTemplate jmsTemplate;
	private ObjectMapper objectMapper;

	@JmsListener(destination = "trainingDurationQueue")
	public void receiveMessage(String json) {
		log.info("Receiving message started");
		try {
			TrainingDurationCountDto trainingDurationCountDto = objectMapper.readValue(json, TrainingDurationCountDto.class);
			trainerService.modifyTrainerWorkingTimeDuration(trainingDurationCountDto);
			log.info("Trainer working time duration successfully changed");
			jmsTemplate.convertAndSend("trainingDurationResponseQueue", "Success");
		} catch (Exception e) {
			log.error("Error while modifying trainer working time duration: {}", e.getMessage());
			jmsTemplate.convertAndSend("trainingDurationResponseQueue", "Error: " + e.getMessage());
		}
	}

}
