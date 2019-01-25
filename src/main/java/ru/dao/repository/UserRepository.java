package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.User;

public interface UserRepository extends DataTablesRepository<User, Integer> {
}
