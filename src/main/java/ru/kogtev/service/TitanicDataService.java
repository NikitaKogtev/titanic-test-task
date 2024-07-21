package ru.kogtev.service;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kogtev.model.PClass;
import ru.kogtev.model.Passenger;
import ru.kogtev.model.Sex;
import ru.kogtev.repository.PassengerRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TitanicDataService {
    private static final Logger logger = LogManager.getLogger(TitanicDataService.class);

    private static final String FILE_URL = "https://web.stanford.edu/class/archive/cs/cs109/cs109.1166/stuff/titanic.csv";
    private static final String SEPARATOR = ",";
    private static final String ONE = "1";

    private final PassengerRepository passengerRepository;

    @Autowired
    public TitanicDataService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @PostConstruct
    public void savePassengersToData() {
        List<Passenger> passengers = loadPassengersFromCsv();

        if (passengerRepository.count() == 0) {
            passengerRepository.saveAll(passengers);
            logger.info("Passengers successful save into DB");
        }
    }

    public List<Passenger> loadPassengersFromCsv() {
        List<Passenger> passengers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(FILE_URL).openStream()))) {
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                Passenger passenger = new Passenger();
                passenger.setSurvived(parts[0].equals(ONE));
                passenger.setPClass(PClass.values()[Integer.parseInt(parts[1]) - 1]);
                passenger.setName(parts[2]);
                passenger.setSex(Sex.valueOf(parts[3].toUpperCase()));
                passenger.setAge((int) Double.parseDouble(parts[4]));
                passenger.setSiblingsSpousesAboard(Integer.parseInt(parts[5]));
                passenger.setParentsChildrenAboard(Integer.parseInt(parts[6]));
                passenger.setFare(Double.parseDouble(parts[7]));
                passengers.add(passenger);
            }
        } catch (IOException e) {
            logger.error("Input/Output problem with csv file: {}", e.getMessage());
        }
        return passengers;
    }
}
