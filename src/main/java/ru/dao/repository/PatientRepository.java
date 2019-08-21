package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.City;
import ru.dao.entity.Patient;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PatientRepository extends DataTablesRepository<Patient,Integer> {
    Optional<Patient> findByFullNameAndCity(String fullname, City city);
}
