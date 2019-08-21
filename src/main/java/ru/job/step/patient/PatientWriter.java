package ru.job.step.patient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import ru.dao.entity.City;
import ru.dao.entity.Patient;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PatientRepository;

import java.util.List;

@Slf4j
public class PatientWriter implements ItemWriter<Patient> {
    private final PatientRepository patientRepository;

    public PatientWriter( PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void write(List<? extends Patient> list) throws Exception {
        list.forEach(patient->{
            try {
                patient.increaseUpdates();
                patientRepository.save(patient);
            } catch (Exception e){
                log.error("Unable to save patient {}", patient.getFullName());
                e.printStackTrace();
            }
        });
    }
}
