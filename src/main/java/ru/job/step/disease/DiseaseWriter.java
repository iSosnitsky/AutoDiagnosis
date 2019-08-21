package ru.job.step.disease;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import ru.dao.entity.City;
import ru.dao.entity.Pathology;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PathologyRepository;

import java.nio.file.Path;
import java.util.List;

@Slf4j
public class DiseaseWriter implements ItemWriter<Pathology> {
    private final PathologyRepository pathologyRepository;

    public DiseaseWriter(PathologyRepository pathologyRepository) {
        this.pathologyRepository = pathologyRepository;
    }

    @Override
    public void write(List<? extends Pathology> list) throws Exception {
        list.forEach(pathology -> {
            try {
                pathologyRepository.save(pathology);
                log.info("Pathology {} saved", pathology.getName());
            } catch (Exception e) {
                log.error("Unable to save pathology {}", pathology.getName());
                e.printStackTrace();
            }
        });
    }
}
