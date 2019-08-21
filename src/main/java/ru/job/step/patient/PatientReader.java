package ru.job.step.patient;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import ru.dao.entity.City;
import ru.dao.entity.Patient;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PatientRepository;
import ru.service.DataService;

import java.util.List;
import java.util.stream.Collectors;

public class PatientReader implements ItemReader<Patient> {
    private final CityRepository cityRepository;
    private final PatientRepository patientRepository;
    private final DataService dataService;
    private ItemReader<Patient> delegate;
    private final String cityName;

    public PatientReader(CityRepository cityRepository, PatientRepository patientRepository, DataService dataService, String cityName) {
        this.cityRepository = cityRepository;
        this.patientRepository = patientRepository;
        this.dataService = dataService;
        this.cityName = cityName;
    }

    @Override
    public Patient read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (delegate == null) {
            delegate = new IteratorItemReader<Patient>(this.getPatients());
        }
        return delegate.read();
    }

    private List<Patient> getPatients() {
        try {
            City city = cityRepository.findFirstByName(cityName)
                    .orElseThrow(() -> new IllegalArgumentException("City " + cityName + " was not found"));
            return dataService.getNewPatientsForCity(cityName)
                    .stream()
                    .map(patientMap -> patientRepository.findByFullNameAndCity(patientMap.get("fullName"), city)
                            .orElseGet(() -> {
                                Patient newPatient = new Patient();
                                newPatient.setFullName(patientMap.get("fullName"));
                                newPatient.setCity(city);
                                newPatient.setUpdates(-1);
                                return newPatient;
                            })).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

