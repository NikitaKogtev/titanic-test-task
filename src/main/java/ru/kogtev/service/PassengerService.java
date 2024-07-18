package ru.kogtev.service;

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
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Page<Passenger> searchPassengers(String searchName, Pageable pageable) {
        if (searchName != null && !searchName.isEmpty()) {
            return passengerRepository.findByNameContainingIgnoreCase(searchName, pageable);
        } else {
            return passengerRepository.findAll(pageable);
        }
    }
}
