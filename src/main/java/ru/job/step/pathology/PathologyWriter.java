package ru.job.step.pathology;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import ru.dao.entity.Pathology;
import ru.dao.repository.PathologyRepository;

import java.util.List;

@Slf4j
public class PathologyWriter implements ItemWriter<Pathology> {
    private final PathologyRepository pathologyRepository;

    public PathologyWriter(PathologyRepository pathologyRepository) {
        this.pathologyRepository = pathologyRepository;
    }

    @Override
    public void write(List<? extends Pathology> list) throws Exception {
        list.forEach(pathologyRepository::save);
    }
}
