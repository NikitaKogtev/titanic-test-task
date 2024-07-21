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
import ru.kogtev.model.Sex;
import ru.kogtev.service.PassengerService;

import java.util.Objects;

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
            @RequestParam(value = "size", defaultValue = "50") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder,
            @RequestParam(value = "name", required = false) String searchName,
            @RequestParam(value = "survived", required = false) Boolean survived,
            @RequestParam(value = "minAge", required = false) Integer minAge,
            @RequestParam(value = "sex", required = false, defaultValue = "") String sex,
            @RequestParam(value = "hasRelatives", required = false) Boolean hasRelatives,
            Model model
    ) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Sex sexParam = (Objects.equals(sex, ""))
                ? null
                : Sex.valueOf(sex.toUpperCase());

        Page<Passenger> passengers = passengerService.searchPassengers(searchName, survived, minAge, sexParam, hasRelatives, pageable);

        Double totalFare = passengerService.getTotalFare(searchName);
        Long relativesCount = passengerService.getRelativesCount(searchName);
        Long survivorsCount = passengerService.getSurvivorsCount(searchName);

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
        return "passengers";
    }
}
