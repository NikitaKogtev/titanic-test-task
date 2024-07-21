package ru.kogtev.service;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

/**
 * Сервис для загрузки и сохранения данных о пассажирах Титаника в базу данных.
 */
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

    /**
     * Метод, который вызывается после создания бина для сохранения данных о пассажирах из CSV файла в базу данных.
     * Если пассажиры уже есть в базе, они не будут сохранены повторно.
     */
    @PostConstruct
    public void savePassengersToData() {
        logger.info("Starting to load passengers from CSV.");

        List<Passenger> passengers = loadPassengersFromCsv();

        if (passengerRepository.count() == 0) {
            logger.info("Saving passengers to the database.");
            passengerRepository.saveAll(passengers);
            logger.info("Passengers successful save into DB");
        } else {
            logger.info("Passengers already exist in the database.");
        }
    }

    /**
     * Метод для загрузки данных о пассажирах из CSV файла в базу данных.
     * Кэшируется результат для улучшения производительности.
     *
     * @return Список объектов Passenger
     */
    @CacheEvict(value = "passengers", allEntries = true)
    public List<Passenger> loadPassengersFromCsv() {
        List<Passenger> passengers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(FILE_URL).openStream()))) {
            String line;
            bufferedReader.readLine();

            logger.info("Reading passengers data from CSV.");

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
            logger.info("Successfully read {} passengers from CSV.", passengers.size());
        } catch (IOException e) {
            logger.error("Input/Output problem with CSV file: {}", e.getMessage());
        }
        return passengers;
    }
}
