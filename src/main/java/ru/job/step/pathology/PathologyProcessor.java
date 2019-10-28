package ru.job.step.pathology;

import org.springframework.batch.item.ItemProcessor;
import ru.dao.entity.Pathology;
import ru.dao.entity.Symptom;
import ru.dao.repository.PathologyRepository;
import ru.dao.repository.SymptomRepository;
import ru.service.DataService;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PathologyProcessor implements ItemProcessor<String, Pathology> {
    private final SymptomRepository symptomRepository;
    private final PathologyRepository pathologyRepository;
    private final DataService dataService;

    public PathologyProcessor(SymptomRepository symptomRepository, PathologyRepository pathologyRepository, DataService dataService) {
        this.symptomRepository = symptomRepository;
        this.pathologyRepository = pathologyRepository;
        this.dataService = dataService;
    }

    @Override
    public Pathology process(String pathologyName) throws Exception {
        Pathology pathology = pathologyRepository.findByName(pathologyName)
                .orElseGet(() -> {
                    Pathology newPathology = new Pathology();
                    newPathology.setName(pathologyName);                   
                    newPathology.setDescription("Newly detected pathology.");
                    return newPathology;
                });
        if (pathology.getId()!=null){
            pathology.setDescription("This pathology was observed before");
        }       
        Set<String> symptomNames = dataService.getSymptomsForPathology(pathologyName);
        Set<Symptom> symptoms = symptomNames.stream()
                .map(symptomName -> symptomRepository.findFirstByName(symptomName)
                        .orElseGet(()->{
                            Symptom newSymptom = new Symptom();
                            newSymptom.setName(symptomName);
                            symptomRepository.save(newSymptom);
                            return newSymptom;
                        }))
                .collect(Collectors.toSet());
        pathology.setSymptoms(symptoms);

        return pathology;
    }
}
