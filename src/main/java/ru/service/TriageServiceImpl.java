package ru.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;
import ru.dao.entity.Patient;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PatientRepository;

import static java.lang.Math.round;

@Service
@Log
public class TriageServiceImpl implements TriageService {
    private final PatientRepository patientRepository;
    private final CityRepository cityRepository;


    @Override
    public Set<String> triageByCity(String cityName) {
        // System.out.println(cityName);
        // Optional<City> city = cityRepository.findFirstByName(cityName);

        // if (city.isPresent()) {
        // log.info("optional success"+city.get());
        // Set<Patient> allPatients = patientRepository.orderedByCity(city.get());
        // allPatients.forEach(System.out::print);
        // }
        // List<Patient> allPatients = patientRepository.orderedByCityName(cityName);
        // allPatients.forEach(System.out::print);
        return new HashSet<>();
    }

    @Override
    public Set<String> triage() {
        Set<Patient> patients = patientRepository.orderedPatients();
        long selected = round(patients.size()*0.30);
        
        return             
    }

    @Autowired
    public TriageServiceImpl(PatientRepository patientRepository, CityRepository cityRepository) {
        this.patientRepository = patientRepository;
        this.cityRepository = cityRepository;
    }

}
