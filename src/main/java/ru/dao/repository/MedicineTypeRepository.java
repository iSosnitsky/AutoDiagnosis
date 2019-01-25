package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.MedicineType;

public interface MedicineTypeRepository extends DataTablesRepository<MedicineType, Integer> {
}
