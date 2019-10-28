package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.City;
import ru.dao.entity.Patient;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PatientRepository extends DataTablesRepository<Patient,Integer> {
    Optional<Patient> findByFullNameAndCity(String fullname, City city);
    
    @Query("select p from Patient p where p.city.name = :cityname order by p.probablePathology.severity desc")
    List<Patient> orderedByCityName(@Param("cityname") String cityname);

    @Query("select p from Patient p where p.city = :city")
    Set<Patient> orderedByCity(@Param("city") City city);

    @Query("select p from Patient p order by p.probablePathology.severity desc")
    Set<Patient> orderedPatients();

}
