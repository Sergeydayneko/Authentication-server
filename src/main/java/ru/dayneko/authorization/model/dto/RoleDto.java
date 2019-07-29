package ru.dayneko.authorization.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
final public class RoleDto {
    private String name;
    private List<UserDto> users;
    private List<ActionDtoWithNameGroupUrl> actions;
}
