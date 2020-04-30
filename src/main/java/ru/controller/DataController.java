package ru.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dao.entity.SensorReading;
import ru.dao.repository.SensorReadingRepository;
import ru.service.SensorReadingService;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final SensorReadingService sensorReadingService;
    private final SensorReadingRepository sensorReadingRepository;

    @GetMapping("getNewReading")
    public SensorReading getNewSensorReading() {
        return sensorReadingService.readNewSensorReading();
    }

    @GetMapping("getLastReading")
    public SensorReading getLastSensorReading() {
        return sensorReadingRepository.findTop1ByIdIsNotNullOrderByIdDesc()
                .orElse(null);
    }

    @GetMapping("getAllReadings")
    public List<SensorReading> getAllSensorReading() {
        return sensorReadingRepository.findAll();
    }

}
