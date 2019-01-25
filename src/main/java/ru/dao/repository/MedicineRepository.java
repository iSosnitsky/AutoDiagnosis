package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.Medicine;

public interface MedicineRepository extends DataTablesRepository<Medicine, Integer> {
}
