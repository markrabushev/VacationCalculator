package com.example.vacationcalculator.controller;


import com.example.vacationcalculator.service.VacationPayService;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class VacationCalculatorController {
    private final VacationPayService vacationPayService;

    public VacationCalculatorController(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<Double> calculateVacationPay(
            @RequestParam @Min(0) double averageSalary,
            @RequestParam(required = false) @Min(1) Integer vacationDays,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        validateParameters(vacationDays, startDate, endDate);

        double amount;
        if (startDate != null) {
            amount = vacationPayService.calculateVacationPay(averageSalary, startDate, endDate);
        } else {
            amount = vacationPayService.calculateVacationPay(averageSalary, vacationDays);
        }

        return ResponseEntity.ok(amount);
    }

    private void validateParameters(Integer vacationDays, LocalDate startDate, LocalDate endDate) {
        if (vacationDays != null && (startDate != null || endDate != null)) {
            throw new IllegalArgumentException("You cannot specify vacation days and vacation dates simultaneously.");
        }
        if (startDate == null && endDate == null && vacationDays == null) {
            throw new IllegalArgumentException("Specify either vacation days or the vacation period");
        }
        if (vacationDays == null && (startDate == null || endDate == null)) {
            throw new IllegalArgumentException("Both the StartDate and EndDate parameters must be specified");
        }
        if (startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("The endDate must be after the startDate");
        }
    }
}
