package com.epam.trainingdurationmicroservice.repository;

import com.epam.trainingdurationmicroservice.entity.Trainer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends MongoRepository<Trainer, String> {

	Optional<Trainer> findByUsername(String username);

}