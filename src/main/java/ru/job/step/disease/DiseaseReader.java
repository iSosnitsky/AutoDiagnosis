package ru.job.step.disease;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import ru.dao.repository.CityRepository;
import ru.service.DataService;

import java.util.Map;

public class DiseaseReader implements ItemReader<String> {
    private final DataService dataService;

    private ItemReader<String> delegate;

    public DiseaseReader(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (delegate == null) {
            delegate = new IteratorItemReader<>(dataService.getNewDiseases());
        }
        return delegate.read();
    }
}
