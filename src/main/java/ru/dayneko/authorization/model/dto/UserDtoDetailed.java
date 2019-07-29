package ru.dayneko.authorization.model.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
final public class UserDtoDetailed {
    private Long id;
    private String fio;
    private String login;
    private String password;
    private String mail;
    private List<RoleNameAndActionsDto> roles;
}
