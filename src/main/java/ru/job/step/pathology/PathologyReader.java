package ru.job.step.pathology;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import ru.service.DataService;

public class PathologyReader implements ItemReader<String> {
    private final DataService dataService;

    private ItemReader<String> delegate;

    public PathologyReader(DataService dataService) {
        this.dataService = dataService;
    }


    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (delegate == null) {
            delegate = new IteratorItemReader<>(dataService.getNewPathologies());
        }
        return delegate.read();
    }
}
