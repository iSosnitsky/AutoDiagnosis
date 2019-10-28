package ru.dao.entity;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.transaction.annotation.Transactional;
import ru.constant.UserRole;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Table(name = "users",indexes = {
        @Index(name = "users_id_index", columnList = "id"),
        @Index(name = "users_login_index", columnList = "login", unique = true)
})
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(DataTablesOutput.View.class)
    @Column
    private Integer id;

    @Column(unique = true)
    @JsonView(DataTablesOutput.View.class)
    private String login;

    @Column
    @JsonView(DataTablesOutput.View.class)
    private String username;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    @JsonView(DataTablesOutput.View.class)
    private UserRole role = UserRole.USER;

}
