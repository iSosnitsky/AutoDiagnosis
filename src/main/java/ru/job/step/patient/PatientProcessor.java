package ru.job.step.patient;

import org.springframework.batch.item.ItemProcessor;
import ru.dao.entity.Pathology;
import ru.dao.entity.Patient;
import ru.dao.entity.Symptom;
import ru.dao.repository.PathologyRepository;
import ru.dao.repository.SymptomRepository;
import ru.service.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class PatientProcessor implements ItemProcessor<Patient, Patient> {
    private final DataService dataService;
    private final SymptomRepository symptomRepository;
    private final PathologyRepository pathologyRepository;
    private final Random random = new Random();

    public PatientProcessor(DataService dataService, SymptomRepository symptomRepository, PathologyRepository pathologyRepository) {
        this.dataService = dataService;
        this.symptomRepository = symptomRepository;
        this.pathologyRepository = pathologyRepository;
    }

    @Override
    public Patient process(Patient patient) throws Exception {
        Set<Symptom> symptoms = dataService.getSymptomsForPatient(patient.getFullName())
                .stream()
                .map(symptomName -> symptomRepository.findFirstByName(symptomName)
                        .orElseGet(()->{
                            Symptom symptom = new Symptom();
                            symptom.setName(symptomName);
                            symptomRepository.save(symptom);
                            return symptom;
                        }))
                .collect(Collectors.toSet());
        patient.setSymptoms(symptoms);
        List<Pathology> possiblePathologies = pathologyRepository.findAllBySymptomIdContaining(new ArrayList<>(symptoms.stream().map(Symptom::getId).collect(Collectors.toList())));
        if (possiblePathologies.size()>=1){
            patient.setProbablePathology(possiblePathologies.get(random.nextInt(possiblePathologies.size())));
        }
        return patient;
    }
}
