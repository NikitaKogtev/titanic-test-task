package ru.kogtev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kogtev.model.Passenger;
import ru.kogtev.model.Sex;
import ru.kogtev.repository.PassengerRepository;

/**
 * Сервис для выполнения операций с пассажирами.
 */
@Service
@Transactional(readOnly = true)
public class PassengerService {
    private static final Logger logger = LogManager.getLogger(PassengerService.class);

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    /**
     * Поиск пассажиров по различным критериям.
     *
     * @param searchName Имя для поиска
     * @param survived Выживший пассажир (true - выжил, false - погиб)
     * @param minAge Минимальный возраст
     * @param sex Пол
     * @param hasRelatives Наличие родственников
     * @param pageable Параметры пагинации
     * @return Страница пассажиров, соответствующих критериям поиска
     */
    @Cacheable("passengers")
    public Page<Passenger> searchPassengers(String searchName, Boolean survived, Integer minAge, Sex sex,
                                            Boolean hasRelatives, Pageable pageable) {
        logger.info("Searching passengers with filters - searchName: {}, survived: {}, minAge: {}, sex: {}, " +
                "hasRelatives: {}", searchName, survived, minAge, sex, hasRelatives);
        return passengerRepository.findByFilters(searchName, survived, minAge, sex, hasRelatives, pageable);
    }

    /**
     * Получение общей суммы стоимости билетов пассажиров.
     *
     * @param searchName Имя для поиска
     * @return Общая сумма стоимости билетов
     */
    public Double getTotalFare(String searchName) {
        Double totalFare;
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Getting total fare passengers with name containing: {}", searchName);
            totalFare = passengerRepository.getTotalFareByNameContainingIgnoreCase(searchName);
        } else {
            logger.info("Getting total fare all passengers");
            totalFare = passengerRepository.getTotalFare();
        }
        return totalFare;
    }

    /**
     * Получение количества родственников пассажиров.
     *
     * @param searchName Имя для поиска
     * @return Количество родственников
     */
    public Long getRelativesCount(String searchName) {
        Long relativesCount;
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Getting relatives count passengers with name containing: {}", searchName);
            relativesCount = passengerRepository.getRelativesCountByNameContaining(searchName);
        } else {
            logger.info("Getting relatives count all passengers");
            relativesCount = passengerRepository.getRelativesCount();
        }
        return relativesCount;
    }

    /**
     * Получение количества выживших пассажиров.
     *
     * @param searchName Имя для поиска
     * @return Количество выживших пассажиров
     */
    public Long getSurvivorsCount(String searchName) {
        Long survivorsCount;
        if (searchName != null && !searchName.isEmpty()) {
            logger.info("Getting survivors count passengers with name containing: {}", searchName);
            survivorsCount = passengerRepository.getSurvivorsCountByNameContaining(searchName);
        } else {
            logger.info("Getting survivors count for all passengers");
            survivorsCount = passengerRepository.getSurvivorsCount();
        }
        return survivorsCount;
    }
}
