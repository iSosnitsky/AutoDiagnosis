package ru.dao.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import ru.dao.entity.City;

import java.util.Optional;

public interface CityRepository extends DataTablesRepository<City,Integer> {
    Optional<City> findFirstByName(String name);
}
