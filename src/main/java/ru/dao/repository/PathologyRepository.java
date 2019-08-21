package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.Pathology;
import ru.dao.entity.Symptom;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface PathologyRepository extends DataTablesRepository<Pathology, Integer> {
//    List<Pathology> findAllBySymptomsContaining(@Param("symptoms") List<Symptom> symptoms);

    @Query(value = "SELECT * FROM pathologies p where p.id in (select symptom.pathology_id from pathology_to_symptoms symptom where p.id=symptom.pathology_id and symptom.symptom_id in :symptoms)", nativeQuery = true)
    List<Pathology> findAllBySymptomIdContaining(@Param("symptoms") List<Integer> symptomIds);
    Optional<Pathology> findByName(@Param("name") String name);

}
