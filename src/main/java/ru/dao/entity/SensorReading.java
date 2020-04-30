package ru.dao.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@Entity
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@Table(name = "sensor_reading")
@EqualsAndHashCode()
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    @Column
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonView(DataTablesOutput.View.class)
    private LocalDate date;

    @JsonFormat(pattern="HH:mm:ss")
    @JsonView(DataTablesOutput.View.class)
    private LocalTime time;

    @Column
    @JsonView(DataTablesOutput.View.class)
    private Integer humidity;


    @Column
    @JsonView(DataTablesOutput.View.class)
    private Integer temperature;
}
