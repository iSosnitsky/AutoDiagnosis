package ru.service;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestDataServiceTest {

    private TestDataService dataService = new TestDataService();

    @Test
    void getNewCities() {
        List<Map<String,String>> cityList = dataService.getNewCities();
        List<Map<String,String>> patientList = dataService.getNewPatientsForCity("Тюмень");
        assertEquals(cityList.size(),10);
        assertEquals(patientList.size(),100);
    }

}