package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.Pathology;

public interface PathologyRepository extends DataTablesRepository<Pathology, Integer> {
}
