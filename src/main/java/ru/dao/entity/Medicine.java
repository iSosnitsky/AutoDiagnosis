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



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @JsonView(DataTablesOutput.View.class)
    private MedicineType type;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
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
