package ru.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dao.entity.*;
import ru.dao.repository.*;

import javax.validation.Valid;

@RestController("/dataTables")
public class DataTablesController {
    private final MedicineRepository medicineRepo;
    private final PathologyRepository pathologyRepo;
    private final MedicineTypeRepository medTypeRepo;
    private final UserRepository userRepo;
    private final UseTypeRepository useTypeRepo;
    private final SymptomRepository symptomRepo;


    @Autowired
    public DataTablesController(MedicineRepository medicineRepo, PathologyRepository pathologyRepo, MedicineTypeRepository medTypeRepo, UserRepository userRepo, UseTypeRepository useTypeRepo, SymptomRepository symptomRepo) {
        this.medicineRepo = medicineRepo;
        this.pathologyRepo = pathologyRepo;
        this.medTypeRepo = medTypeRepo;
        this.userRepo = userRepo;
        this.useTypeRepo = useTypeRepo;
        this.symptomRepo = symptomRepo;
    }

    @PostMapping("/medicines")
    public DataTablesOutput<Medicine> medicines(@Valid @RequestBody DataTablesInput input){
        return medicineRepo.findAll(input);
    }

    @PostMapping("/pathologies")
    public DataTablesOutput<Pathology> pathologies(@Valid @RequestBody DataTablesInput input){
        return pathologyRepo.findAll(input);
    }

    @PostMapping("/medicineTypes")
    public DataTablesOutput<MedicineType> medicineTypes(@Valid @RequestBody DataTablesInput input){
        return medTypeRepo.findAll(input);
    }

    @PostMapping("/users")
    public DataTablesOutput<User> users(@Valid @RequestBody DataTablesInput input){
        return userRepo.findAll(input);
    }

    @PostMapping("/useTypes")
    public DataTablesOutput<UseType> useTypes(@Valid @RequestBody DataTablesInput input){
        return useTypeRepo.findAll(input);
    }
    @PostMapping("/symptoms")
    public DataTablesOutput<Symptom> symptoms(@Valid @RequestBody DataTablesInput input){
        return symptomRepo.findAll(input);
    }
}
