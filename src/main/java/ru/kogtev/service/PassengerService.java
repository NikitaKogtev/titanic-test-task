package ru.kogtev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kogtev.model.Passenger;
import ru.kogtev.repository.PassengerRepository;

@Service
@Transactional(readOnly = true)
public class PassengerService {
    private static final Logger logger = LogManager.getLogger(PassengerService.class);
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Page<Passenger> searchPassengers(String searchName, Pageable pageable) {
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Searching passengers with name containing: {}", searchName);
            return passengerRepository.findByNameContaining(searchName, pageable);
        } else {
            logger.info("Fetching all passengers");
            return passengerRepository.findAll(pageable);
        }
    }

    public Double getTotalFare(String searchName) {
        Double totalFare;
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Calculating total fare for passengers with name containing: {}", searchName);
            totalFare = passengerRepository.getTotalFareByNameContainingIgnoreCase(searchName);
        } else {
            logger.info("Calculating total fare for all passengers");
            totalFare = passengerRepository.getTotalFare();
        }
        logger.info("Total fare calculated: {}", totalFare);
        return totalFare;
    }

    public Long getRelativesCount(String searchName) {
        Long relativesCount;
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Counting passengers with relatives and name containing: {}", searchName);
            relativesCount = passengerRepository.getRelativesCountByNameContaining(searchName);
        } else {
            logger.info("Counting passengers with relatives for all passengers");
            relativesCount = passengerRepository.getRelativesCount();
        }
        logger.info("Relatives count calculated: {}", relativesCount);
        return relativesCount;
    }

    public Long getSurvivorsCount(String searchName) {
        Long survivorsCount;
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Counting survivors for passengers with name containing: {}", searchName);
            survivorsCount = passengerRepository.getSurvivorsCountByNameContaining(searchName);
        } else {
            logger.info("Counting survivors for all passengers");
            survivorsCount = passengerRepository.getSurvivorsCount();
        }
        logger.info("Survivors count calculated: {}", survivorsCount);
        return survivorsCount;
    }
}
