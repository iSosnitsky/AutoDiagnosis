package triagetest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.dao.entity.City;
import ru.dao.entity.Pathology;
import ru.dao.entity.Patient;
import ru.dao.repository.CityRepository;
import ru.dao.repository.PatientRepository;
import ru.service.TriageServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@DisplayName("Mockito tests for Triage Service Implementation")
public class TriageServiceTest {
    private final static Integer PERCENT_TO_HOSPITALIZE = 30;
    @Mock
    private PatientRepository patientRepositoryMock;
    @Mock
    private CityRepository cityRepositoryMock;
    @InjectMocks
    private TriageServiceImpl triageServiceMock;

    private City tCity = new City(1, "Москва", "", 0);
    private List<City> cities = new ArrayList<>();
    private Pathology tPathology = new Pathology();
    private Set<Patient> patients =
            Set.of(new Patient(1, "Пациент 1", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(2, "Пациент 2", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(3, "Пациент 3", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(4, "Пациент 4", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(5, "Пациент 5", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(6, "Пациент 6", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(7, "Пациент 7", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(8, "Пациент 8", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(9, "Пациент 9", 0, false, tCity, new HashSet<>(), tPathology),
                    new Patient(10, "Пациент 10", 0, false, tCity, new HashSet<>(), tPathology));

    @Test
    @DisplayName("Triage")
    public void testTriage() {
        Assertions.assertNotNull(patientRepositoryMock);
        Assertions.assertNotNull(cityRepositoryMock);
        Assertions.assertNotNull(triageServiceMock);

        cities.add(tCity);
        when(cityRepositoryMock.findAll()).thenReturn(cities);

        doAnswer((i) -> {
            return patients.stream().map(p -> {
                p.setHospitalized(false);
                return p;
            }).collect(Collectors.toSet());
        }).when(patientRepositoryMock).clearAllHospitalized();

        long c = java.lang.Math.round(patients.size() * PERCENT_TO_HOSPITALIZE / 100.0);

        when(patientRepositoryMock.percentedByCityName(anyString(), anyInt()))
                .thenReturn(patients.stream().limit(c).map(p -> {
                    p.setHospitalized(true);
                    return p;
                }).collect(Collectors.toSet()));

        when(patientRepositoryMock.findByHospitalizedTrue()).thenReturn(
                patients.stream().filter(p -> p.getHospitalized()).collect(Collectors.toSet()));

        Set<String> resultTriage = triageServiceMock.triage();
        Assertions.assertEquals(3, resultTriage.size());

    }

    @Test
    @DisplayName("TriageByCity")
    public void testTriageByCity() {
        Assertions.assertNotNull(patientRepositoryMock);
        Assertions.assertNotNull(cityRepositoryMock);
        Assertions.assertNotNull(triageServiceMock);

        doAnswer((i) -> {
            return patients.stream().map(p -> {
                p.setHospitalized(false);
                return p;
            }).collect(Collectors.toSet());
        }).when(patientRepositoryMock).clearByCityNameHospitalized(anyString());

        long c = java.lang.Math.round(patients.size() * PERCENT_TO_HOSPITALIZE / 100.0);

        when(patientRepositoryMock.percentedByCityName(anyString(), anyInt()))
                .thenReturn(patients.stream().limit(c).map(p -> {
                    p.setHospitalized(true);
                    return p;
                }).collect(Collectors.toSet()));


        when(patientRepositoryMock.hospitalizedByCity(anyString())).thenReturn(
                patients.stream().filter(p -> p.getHospitalized()).collect(Collectors.toSet()));
        Set<String> resultTriage = triageServiceMock.triageByCity("Москва");
        Assertions.assertEquals(3, resultTriage.size());
    }
}
