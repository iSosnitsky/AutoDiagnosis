package ru.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Table(name = "pathologies")
@EqualsAndHashCode(exclude = {"symptoms","medicines"})
public class Pathology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    @Column
    private Integer id;


    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String name;

    @Column
    @JsonView(DataTablesOutput.View.class)
    private String description;



    @JsonView(DataTablesOutput.View.class)
    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "pathology_to_symptoms",
            joinColumns = { @JoinColumn(name = "pathology_id") },
            inverseJoinColumns = { @JoinColumn(name = "symptom_id") },
            indexes = {@Index(name = "pathology_to_symptoms_pathology_id_index", columnList = "pathology_id"),
                    @Index(name = "pathology_to_symptoms_symptom_id_index", columnList = "symptom_id")}
    )
    private Set<Symptom> symptoms = new HashSet<>();



    @JsonView(DataTablesOutput.View.class)
    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
            name = "pathology_to_medicine",
            joinColumns = { @JoinColumn(name = "pathology_id") },
            inverseJoinColumns = { @JoinColumn(name = "medicine_id") },
            indexes = {@Index(name = "pathology_to_medicines_pathology_id_index", columnList = "pathology_id"),
                    @Index(name = "pathology_to_medicines_medicine_id_index", columnList = "medicine_id")}
    )
    private Set<Medicine> medicines = new HashSet<>();
    
}
