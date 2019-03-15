package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.Pathology;
import ru.dao.entity.Symptom;

import java.util.List;

public interface PathologyRepository extends DataTablesRepository<Pathology, Integer> {
    List<Pathology> findAllBySymptomsContaining(@Param("symptoms") List<Symptom> symptoms);

}
