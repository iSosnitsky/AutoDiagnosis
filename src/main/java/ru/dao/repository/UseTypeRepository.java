package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.UseType;

public interface UseTypeRepository extends DataTablesRepository<UseType, Integer> {
}
