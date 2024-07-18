package ru.kogtev.controller;

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
import ru.kogtev.service.PassengerService;

@Controller
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/passengers")
    public String showPassengersPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "50") int size, // Размер страницы
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name")); // Сортировка по имени
        Page<Passenger> passengers = passengerService.getAllPassengers(pageable); // Получение пассажиров

        model.addAttribute("passengers", passengers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", passengers.getTotalPages());
        model.addAttribute("pageSize", size); // Передача размера страницы в модель
        return "passengers";
    }
}
