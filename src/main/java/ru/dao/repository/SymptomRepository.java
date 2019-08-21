package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.MedicineType;
import ru.dao.entity.Symptom;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SymptomRepository extends DataTablesRepository<Symptom, Integer> {
    public List<Symptom> findTop10ByNameContaining (@Param("name") String name);

    public Optional<Symptom> findFirstByName(@Param("name") String name);
}
