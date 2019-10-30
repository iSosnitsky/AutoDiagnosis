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
    Set<Patient> findByHospitalizedTrue();
    
    @Query(value = "select * from patients as p left join cities as c on p.city_id=c.id  left join pathologies as pt on  p.probable_pathology_id = pt.id where c.name = :cityname order by ifnull(pt.severity,0) desc fetch first :percent percent rows only", nativeQuery = true)
    Set<Patient> percentedByCityName(@Param("cityname") String cityname, @Param("percent") Integer percent);

    @Query(value = "select * from patients as p left join cities as c on p.city_id=c.id where c.name = :cityname and p.hospitalized", nativeQuery = true)
    Set<Patient> hospitalizedByCity(@Param("cityname") String cityname);

    @Query(value = "update patients set hospitalized = false", nativeQuery = true)
    void clearHospitalized();
}
