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
@Table(name = "cities")
@EqualsAndHashCode
public class City {
    public City(City oldCity){
        this.id=oldCity.id;
        this.name = oldCity.name;
        this.region = oldCity.region;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    @Column
    private Integer id;


    @Column(nullable = false,unique = true)
    @JsonView(DataTablesOutput.View.class)
    private String name;

    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String region;

    @Column(nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private Integer updates;

}
