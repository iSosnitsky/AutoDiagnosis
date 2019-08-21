package ru.service;

import org.springframework.stereotype.Service;
import ru.constant.test.Cities;
import ru.constant.test.Names;

import java.util.*;
import java.util.stream.Collectors;

import static ru.constant.test.Names.*;

@Service
public class TestDataService implements DataService {
    private List<Cities> givenCities = new ArrayList<>();
    private final Random random = new Random();
    private Set<String> givenSymptomsAtAllTime = new HashSet<>();

    @Override
    public List<Map<String, String>> getNewCities() {
        givenCities.clear();
        Cities[] cities = Cities.values();
        for (int i = 0; i < 10; i++) {
            givenCities.add(cities[random.nextInt(cities.length)]);
        }
        return givenCities.stream()
                .map(city -> new HashMap<String, String>() {{
                    put("name", city.getName());
                    put("region", city.getRegion());
                }}).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getLastGivenCities() {
        return givenCities.stream()
                .map(Cities::getName)
                .collect(Collectors.toList());
    }


    @Override
    public List<Map<String, String>> getNewPatientsForCity(String city) {
        List<Map<String, String>> patientSet = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            patientSet.add(new HashMap<String, String>() {{
                put("fullName", NAMES[random.nextInt(NAMES.length)] + " " + SURNAMES[random.nextInt(SURNAMES.length)]);
                put("city", city);
            }});
        }
        return patientSet;
    }

    @Override
    public List<String> getNewDiseases(){
        Set<String> diseaseList = new HashSet<>();
        for (int i = 0; i < 10;i++){
            diseaseList.add(DISEASES[random.nextInt(DISEASES.length)]);
        }
        return new ArrayList<>(diseaseList);
    }

    @Override
    public Set<String> getSymptomsForDisease(String diseaseName){
        Set<String> symptomList = new HashSet<>();
        for (int i = 1; i <= random.nextInt(10);i++){
            symptomList.add(SYMPTOMS[random.nextInt(SYMPTOMS.length)]);
        }
        givenSymptomsAtAllTime.addAll(symptomList);
        return symptomList;
    }

    @Override
    public Set<String> getSymptomsForPatient(String patientName){
        Set<String> symptomList = new HashSet<>();
        String[] givenSymptomsArray = givenSymptomsAtAllTime.toArray(new String[givenSymptomsAtAllTime.size()]);
        for (int i = 1; i <= random.nextInt(10);i++){
            symptomList.add(givenSymptomsArray[random.nextInt(givenSymptomsArray.length)]);
        }
        return symptomList;
    }
}
