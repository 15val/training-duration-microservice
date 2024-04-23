package com.epam.trainingdurationmicroservice.repository;


import com.epam.trainingdurationmicroservice.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
	Optional<Trainer> findByUsername(String username);

}