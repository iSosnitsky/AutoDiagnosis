package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.MedicineType;
import ru.dao.entity.UseType;

import java.util.List;

public interface UseTypeRepository extends DataTablesRepository<UseType, Integer> {
    public List<UseType> findTop10ByNameContaining (@Param("name") String name);
}
