package ru.dao.entity;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Table(name = "medicines")
@EqualsAndHashCode(exclude = {"type","useType"})
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    @Column
    private Integer id;


    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEDICINE_TYPE_ID")
    @JsonView(DataTablesOutput.View.class)
    private MedicineType type;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USE_TYPE_ID")
    @JsonView(DataTablesOutput.View.class)
    private UseType useType;


    @Column
    @JsonView(DataTablesOutput.View.class)
    private String substanceDescription;


    @Column
    @JsonView(DataTablesOutput.View.class)
    private String description;


    @Column
    @JsonView(DataTablesOutput.View.class)
    private Float price;



}
