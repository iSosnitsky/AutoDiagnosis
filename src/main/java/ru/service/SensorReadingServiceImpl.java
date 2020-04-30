package ru.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dao.entity.SensorReading;
import ru.dao.repository.SensorReadingRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SensorReadingServiceImpl implements SensorReadingService{
    private final SensorService sensorService;
    private final SensorReadingRepository sensorReadingRepository;

    @Override
    public SensorReading readNewSensorReading() {
        SensorReading sensorReading = new SensorReading();
        sensorReading.setHumidity(sensorService.readHumidity());
        sensorReading.setTemperature(sensorService.readTemperature());
        sensorReading.setTime(LocalTime.now());
        sensorReading.setDate(LocalDate.now());
        sensorReadingRepository.save(sensorReading);
        return sensorReading;
    }
}
