package ru.kogtev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kogtev.model.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    Page<Passenger> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
