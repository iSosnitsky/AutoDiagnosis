package ru.job.step.pathology;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.batch.item.ItemWriter;
import ru.dao.entity.Pathology;
import ru.dao.repository.PathologyRepository;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class PathologyWriter implements ItemWriter<Pathology> {
    private final PathologyRepository pathologyRepository;

    public PathologyWriter(PathologyRepository pathologyRepository) {
        this.pathologyRepository = pathologyRepository;
    }

    @Override
    public void write(List<? extends Pathology> list) throws Exception {
        Random random = new Random();
        list.forEach((item) -> {
            Integer tempSeverity = random.nextInt(11);
            item.setSeverity(tempSeverity);
            //log.info(item.toString());
            pathologyRepository.save(item);
        });
    }
}
