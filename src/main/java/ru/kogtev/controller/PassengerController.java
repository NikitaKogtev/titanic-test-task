package ru.kogtev.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kogtev.model.Passenger;
import ru.kogtev.model.Sex;
import ru.kogtev.service.PassengerService;

import java.util.Objects;

/**
 * Контроллер для обработки запросов, связанных с пассажирами.
 */
@Controller
public class PassengerController {
    private static final Logger logger = LogManager.getLogger(PassengerController.class);

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "50";
    private static final String DEFAULT_SORT_BY = "name";
    private static final String DEFAULT_SORT_ORDER = "ASC";

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    /**
     * Метод для отображения страницы с пассажирами.
     * Обрабатывает параметры запроса и возвращает представление с данными.
     *
     * @param page         Номер страницы для пагинации
     * @param size         Размер страницы для пагинации
     * @param sortBy       Поле для сортировки
     * @param sortOrder    Порядок сортировки (ASC - по возрастанию/DESC - по убыванию)
     * @param searchName   Имя для поиска
     * @param survived     Выживший пассажир (true - выжил, false - погиб)
     * @param minAge       Минимальный возраст
     * @param sex          Пол
     * @param hasRelatives Наличие родственников
     * @param model        Модель для передачи данных в представление
     * @return Название представления
     */
    @GetMapping("/passengers")
    public String showPassengersPage(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = DEFAULT_SORT_ORDER) String sortOrder,
            @RequestParam(value = "name", required = false) String searchName,
            @RequestParam(value = "survived", required = false) Boolean survived,
            @RequestParam(value = "minAge", required = false) Integer minAge,
            @RequestParam(value = "sex", required = false, defaultValue = "") String sex,
            @RequestParam(value = "hasRelatives", required = false) Boolean hasRelatives,
            Model model
    ) {

        // Определение направления сортировки по возрастанию и по убыванию
        Sort.Direction direction = sortOrder.equalsIgnoreCase(DEFAULT_SORT_ORDER)
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Преобразование строки пола в перечисление Sex
        Sex sexParam = (Objects.equals(sex, "")) ? null : Sex.valueOf(sex.toUpperCase());

        // Поиск пассажиров с заданными параметрами
        Page<Passenger> passengers = passengerService.searchPassengers(searchName, survived, minAge, sexParam,
                hasRelatives, pageable);

        logger.info("Found {} passengers", passengers.getTotalElements());

        // Получение статистики
        Double totalFare = passengerService.getTotalFare(searchName);
        Long relativesCount = passengerService.getRelativesCount(searchName);
        Long survivorsCount = passengerService.getSurvivorsCount(searchName);

        // Добавление данных в модель для отображения на странице
        model.addAttribute("passengers", passengers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", passengers.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchName", searchName);
        model.addAttribute("totalFare", totalFare);
        model.addAttribute("relativesCount", relativesCount);
        model.addAttribute("survivorsCount", survivorsCount);
        model.addAttribute("survived", survived);
        model.addAttribute("minAge", minAge);
        model.addAttribute("sex", sex);
        model.addAttribute("hasRelatives", hasRelatives);

        logger.info("Passengers view return to model: {}", model);

        return "passengers"; // Название представления
    }
}
