package ru.dayneko.authorization.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
final public class RoleIdAndNameDto {
    private long id;
    private String name;
}
