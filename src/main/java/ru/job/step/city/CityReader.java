package ru.job.step.city;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import ru.dao.entity.City;
import ru.dao.repository.CityRepository;
import ru.service.DataService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CityReader implements ItemReader<Map<String, String>> {
    private final DataService dataService;
    private final CityRepository cityRepository;
    public static final String NAME_FIELD = "name";
    public static final String REGION_FIELD = "region";

    private ItemReader<Map<String, String>> delegate;

    public CityReader(DataService dataService, CityRepository cityRepository) {
        this.dataService = dataService;
        this.cityRepository = cityRepository;
    }


    @Override
    public Map<String, String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (delegate == null) {
            delegate = new IteratorItemReader<>(dataService.getNewCities());
        }
        return delegate.read();
    }
}
