package ru.dayneko.authorization.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
final public class UserDto {
    private Long id;
    private String fio;
    private String login;
    private String password;
    private String mail;
}
