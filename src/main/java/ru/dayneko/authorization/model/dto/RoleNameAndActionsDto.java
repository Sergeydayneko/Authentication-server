package ru.dayneko.authorization.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
final class RoleNameAndActionsDto {
    private String name;
    private List<ActionDtoWithNameGroupUrl> actions;
}
