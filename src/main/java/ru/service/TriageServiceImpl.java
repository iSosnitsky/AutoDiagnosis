package ru.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.java.Log;
import ru.dao.entity.City;
import ru.dao.entity.Patient;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PatientRepository;

import static java.lang.Math.round;

@Service
@Log
public class TriageServiceImpl implements TriageService {
    private final PatientRepository patientRepository;
    private final CityRepository cityRepository;
    private final static Integer PERCENT_TO_HOSPITALIZE = 30;


    @Override
    public Set<String> triageByCity(String cityName) {
        patientRepository.percentedByCityName(cityName, PERCENT_TO_HOSPITALIZE).stream()
                .forEach(p -> {
                    p.setHospitalized(true);
                    patientRepository.save(p);
                });
        return patientRepository.hospitalizedByCity(cityName).stream().map(p -> p.getFullName()
        // +" "+ p.getCity().getName()+" "
        // +p.getProbablePathology().getSeverity() + " " + p.getHospitalized()
        ).collect(Collectors.toSet());
    }

    @Override
    public Set<String> triage() {
        cityRepository.findAll().forEach(c -> {
            patientRepository.percentedByCityName(c.getName(), PERCENT_TO_HOSPITALIZE).stream()
                    .forEach(p -> {
                        p.setHospitalized(true);
                        patientRepository.save(p);
                    });
        });
        return patientRepository.findByHospitalizedTrue().stream().map(p -> p.getFullName())
                .collect(Collectors.toSet());
    }

    @Autowired
    public TriageServiceImpl(PatientRepository patientRepository, CityRepository cityRepository) {
        this.patientRepository = patientRepository;
        this.cityRepository = cityRepository;
    }

}
