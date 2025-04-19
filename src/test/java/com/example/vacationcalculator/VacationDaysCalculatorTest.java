package com.example.vacationcalculator;

import com.example.vacationcalculator.service.VacationDaysCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VacationDaysCalculatorTest {

    @Autowired
    private VacationDaysCalculator calculator;

    @Test
    void calculateVacationDaysWithHolidays() {
        LocalDate start = LocalDate.of(2025, 4, 28);
        LocalDate end = LocalDate.of(2025, 5, 11);
        int days = calculator.calculateWorkingDays(start, end);
        assertEquals(12, days);
    }

    @Test
    void calculateVacationDaysWithoutHolidays() {
        LocalDate start = LocalDate.of(2025, 4, 7);
        LocalDate end = LocalDate.of(2025, 4, 18);
        int days = calculator.calculateWorkingDays(start, end);
        assertEquals(12, days);
    }

    @Test
    void calculateWorkingDaysWithSingleHoliday() {
        LocalDate date = LocalDate.of(2025, 3, 8);
        int days = calculator.calculateWorkingDays(date, date);
        assertEquals(0, days);
    }

    @Test
    void calculateWorkingDaysWithSingleDay() {
        LocalDate date = LocalDate.of(2025, 4, 8);
        int days = calculator.calculateWorkingDays(date, date);
        assertEquals(1, days);
    }

    @Test
    void calculateWorkingDaysCrossYear() {
        LocalDate start = LocalDate.of(2024, 12, 28);
        LocalDate end = LocalDate.of(2025, 1, 3);
        int days = calculator.calculateWorkingDays(start, end);
        assertEquals(4, days);
    }

    @Test
    void calculateWorkingDaysWithHolidays2018() {
        LocalDate start = LocalDate.of(2018, 4, 28);
        LocalDate end = LocalDate.of(2018, 5, 11);
        int days = calculator.calculateWorkingDays(start, end);
        assertEquals(12, days);
    }

}
