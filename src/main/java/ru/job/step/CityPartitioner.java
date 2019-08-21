package ru.job.step;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import ru.dao.repository.CityRepository;
import ru.service.DataService;

import java.util.HashMap;
import java.util.Map;

public class CityPartitioner implements Partitioner {
    private final DataService dataService;

    public CityPartitioner(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String,ExecutionContext> map = new HashMap<>(gridSize);
        dataService.getLastGivenCities().forEach(city -> {
            ExecutionContext context = new ExecutionContext();
            context.putString("city",city);
            map.put(city,context);
        });
        return map;
    }
}
