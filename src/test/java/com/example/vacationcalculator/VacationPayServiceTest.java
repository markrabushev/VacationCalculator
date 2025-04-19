package com.example.vacationcalculator;

import com.example.vacationcalculator.service.VacationDaysCalculator;
import com.example.vacationcalculator.service.VacationPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VacationPayServiceTest {

    @MockitoBean
    private VacationDaysCalculator vacationDaysCalculator;

    @Autowired
    private VacationPayService vacationPayService;

    @Test
    void calculateVacationPayStandard() {
        double result = vacationPayService.calculateVacationPay(50000, 14);
        assertEquals(50000 / 29.3 * 14, result, 0.01);
    }

    @Test
    void calculateVacationPayByDates() {
        LocalDate start = LocalDate.of(2025, 4, 1);
        LocalDate end = LocalDate.of(2025, 4, 5);
        when(vacationDaysCalculator.calculateWorkingDays(start, end)).thenReturn(5);
        double result = vacationPayService.calculateVacationPay(50000, start, end);
        assertEquals(50000 / 29.3 * 5, result, 0.01);
    }

    @Test
    void calculateVacationPayInvalidParamsStandard() {
        assertThrows(IllegalArgumentException.class, () -> vacationPayService.calculateVacationPay(-1, -1));
    }

    @Test
    void calculateVacationPayInvalidParamsByDates() {
        assertThrows(IllegalArgumentException.class, () -> vacationPayService.calculateVacationPay(-1, LocalDate.of(2025, 4, 1), LocalDate.of(2025, 4, 5)));
    }
}
