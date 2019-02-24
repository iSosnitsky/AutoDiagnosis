package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.Medicine;
import ru.dao.entity.MedicineType;

import java.util.List;

public interface MedicineRepository extends DataTablesRepository<Medicine, Integer> {
    public List<Medicine> findTop10ByNameContaining (@Param("name") String name);
}
