package ru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.dao.entity.Medicine;
import ru.dao.repository.MedicineRepository;

@RestController("/data")
public class DataController {

    private final MedicineRepository medicineRepository;

    @Autowired
    public DataController(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @GetMapping("medicine/{medicineId}")
    public Medicine getFullMedicine(@PathVariable("medicineId") Integer medicineId) {
        return medicineRepository
                .findById(medicineId)
                .orElseThrow(()->new IllegalArgumentException("Медицина с таким ID не найдена"));
    }

}
