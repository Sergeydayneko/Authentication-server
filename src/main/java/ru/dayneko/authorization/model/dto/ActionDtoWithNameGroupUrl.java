package ru.dayneko.authorization.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// todo переработать структуру DTO
@NoArgsConstructor
@Setter
@Getter
final public class ActionDtoWithNameGroupUrl {
    private String name;
    private String group;
    private String url;
}
