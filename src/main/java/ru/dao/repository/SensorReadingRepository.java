package ru.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.dao.entity.SensorReading;

import java.util.Optional;

public interface SensorReadingRepository extends JpaRepository<SensorReading,Integer> {
    Optional<SensorReading> findTop1ByIdIsNotNullOrderByIdDesc();
}
