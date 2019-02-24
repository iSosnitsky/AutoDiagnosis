package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.query.Param;
import ru.dao.entity.MedicineType;

import java.util.List;

public interface MedicineTypeRepository extends DataTablesRepository<MedicineType, Integer> {
    public List<MedicineType> findTop10ByNameContaining (@Param ("name") String name);

}
