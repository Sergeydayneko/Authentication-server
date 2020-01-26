package ru.dayneko.authorization.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
final public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private List<User> users;

    @ManyToMany
    @JsonIgnoreProperties("roles")
    @JoinTable(
            name               = "permissions",
            joinColumns        = @JoinColumn(name = "id_role", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_action", referencedColumnName = "id"))
    private List<Action> actions;

}
