package ru.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MockSensorServiceImpl implements SensorService {
    private Integer previousHumidity = 50;
    private Integer previousTemperature = 90;

    Random random = new Random();
    @Override
    public Integer readHumidity() {
        return previousHumidity-(5-random.nextInt(10));
    }

    @Override
    public Integer readTemperature() {
        return previousTemperature-(5-random.nextInt(10));
    }
}
