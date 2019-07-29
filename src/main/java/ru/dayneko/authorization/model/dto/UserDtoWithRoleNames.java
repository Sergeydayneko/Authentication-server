package ru.dayneko.authorization.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Setter
@Getter
@NoArgsConstructor
public class UserDtoWithRoleNames {
    private String fio;
    private String login;
    private String password;
    private String mail;
    private HashSet<String> roles;
}
