package com.example.vacationcalculator.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VacationPayService {
    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;
    private final VacationDaysCalculator vacationDaysCalculator;

    public VacationPayService(VacationDaysCalculator vacationDaysCalculator) {
        this.vacationDaysCalculator = vacationDaysCalculator;
    }

    public double calculateVacationPay(double averageSalary, int vacationDays) {
        if (averageSalary < 0 || vacationDays < 1) {
            throw new IllegalArgumentException("Incorrect parameters");
        }
        return averageSalary / AVERAGE_DAYS_IN_MONTH * vacationDays;
    }

    public double calculateVacationPay(double averageSalary, LocalDate startDate, LocalDate endDate) {
        if (averageSalary < 0) {
            throw new IllegalArgumentException("Incorrect parameters");
        }
        int vacationDays = vacationDaysCalculator.calculateWorkingDays(startDate, endDate);
        return averageSalary / AVERAGE_DAYS_IN_MONTH * vacationDays;
    }

}
