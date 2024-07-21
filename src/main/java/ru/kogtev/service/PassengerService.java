package ru.kogtev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kogtev.model.Passenger;
import ru.kogtev.model.Sex;
import ru.kogtev.repository.PassengerRepository;

@Service
@Transactional(readOnly = true)
public class PassengerService {
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Page<Passenger> searchPassengers(String searchName, Boolean survived, Integer minAge, Sex sex, Boolean hasRelatives, Pageable pageable) {
        return passengerRepository.findByFilters(searchName, survived, minAge, sex, hasRelatives, pageable);
    }

    public Double getTotalFare(String searchName) {
        Double totalFare;
        if (searchName != null && !searchName.isEmpty()) {
            totalFare = passengerRepository.getTotalFareByNameContainingIgnoreCase(searchName);
        } else {
            totalFare = passengerRepository.getTotalFare();
        }
        return totalFare;
    }

    public Long getRelativesCount(String searchName) {
        Long relativesCount;
        if (searchName != null && !searchName.isEmpty()) {
            relativesCount = passengerRepository.getRelativesCountByNameContaining(searchName);
        } else {
            relativesCount = passengerRepository.getRelativesCount();
        }
        return relativesCount;
    }

    public Long getSurvivorsCount(String searchName) {
        Long survivorsCount;
        if (searchName != null && !searchName.isEmpty()) {
            survivorsCount = passengerRepository.getSurvivorsCountByNameContaining(searchName);
        } else {
            survivorsCount = passengerRepository.getSurvivorsCount();
        }
        return survivorsCount;
    }
}
