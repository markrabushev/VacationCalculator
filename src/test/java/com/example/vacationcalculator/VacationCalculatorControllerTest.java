package com.example.vacationcalculator;

import com.example.vacationcalculator.controller.VacationCalculatorController;
import com.example.vacationcalculator.service.VacationPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VacationCalculatorController.class)
public class VacationCalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VacationPayService vacationPayService;

    @Test
    void calculateWithVacationDays() throws Exception {
        when(vacationPayService.calculateVacationPay(50000, 28))
                .thenReturn(50000 / 29.3 * 28);
        double amount = 50000 / 29.3 * 28;
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "50000")
                        .param("vacationDays", "28"))
                .andExpect(status().isOk())
                .andExpect(content().string(Double.toString(amount)));
    }

    @Test
    void calculateWithDates() throws Exception {
        when(vacationPayService.calculateVacationPay(50000,
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 14)
        )).thenReturn(50000 / 29.3 * 14);
        double amount = 50000 / 29.3 * 14;
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "50000")
                        .param("startDate", "2025-04-01")
                        .param("endDate", "2025-04-14"))
                .andExpect(status().isOk())
                .andExpect(content().string(Double.toString(amount)));
    }

    @Test
    void invalidParameters() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "50000")
                        .param("vacationDays", "14")
                        .param("startDate", "2025-04-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void noParametersProvided() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "50000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void onlyOneDateProvided_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "30000")
                        .param("startDate", "2025-04-01"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void endDateBeforeStartDate_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", "30000")
                        .param("startDate", "2025-04-10")
                        .param("endDate", "2025-04-01"))
                .andExpect(status().isBadRequest());
    }

}
