package ru.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DataService {

    List<Map<String,String>> getNewCities();

    List<String> getLastGivenCities();

    List<Map<String, String>> getNewPatientsForCity(String city);

    List<String> getNewPathologies();

    Set<String> getSymptomsForPathology(String diseaseName);

    Set<String> getSymptomsForPatient(String patientName);
}
