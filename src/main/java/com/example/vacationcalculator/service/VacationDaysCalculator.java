package com.example.vacationcalculator.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class VacationDaysCalculator {

    private final List<LocalDate> HOLIDAYS = List.of(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 2),
            LocalDate.of(2025, 1, 3),
            LocalDate.of(2025, 1, 4),
            LocalDate.of(2025, 1, 5),
            LocalDate.of(2025, 1, 6),
            LocalDate.of(2025, 1, 7),
            LocalDate.of(2025, 1, 8),
            LocalDate.of(2025, 2, 23),
            LocalDate.of(2025, 3, 8),
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 9),
            LocalDate.of(2025, 6, 12),
            LocalDate.of(2025, 11, 4)
    );

    private boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(date.withYear(2025));
    }

    public int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        int vacationDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            if (!isHoliday(date)) {
                vacationDays++;
            }
            date = date.plusDays(1);
        }
        return vacationDays;
    }
}
