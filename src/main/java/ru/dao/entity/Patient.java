package ru.dao.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
@EqualsAndHashCode(exclude = {"city"})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    @Column
    private Integer id;


    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String fullName;

    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private Integer updates = -1;

    @Column(nullable = false)    
    @JsonView(DataTablesOutput.View.class)
    private Boolean hospitalized = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @JsonView(DataTablesOutput.View.class)
    private City city;

    @JsonView(DataTablesOutput.View.class)
    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "patients_to_symptoms",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "symptom_id") },
            indexes = {@Index(name = "patient_to_symptoms_patient_id_index", columnList = "patient_id"),
                    @Index(name = "patient_to_symptoms_symptom_id_index", columnList = "symptom_id")}
    )
    private Set<Symptom> symptoms = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @JsonView(DataTablesOutput.View.class)
    private Pathology probablePathology;

    public void increaseUpdates(){
        this.updates= this.updates+1;
    }
}
