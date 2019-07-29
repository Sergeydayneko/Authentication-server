package ru.dayneko.authorization.model.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "actions")
final public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String group;
    private String url;

    // todo переделать всё в Collection(Set)
    @ManyToMany(mappedBy = "actions")
    @JsonIgnoreProperties("actions")
    private List<Role> roles;
}
