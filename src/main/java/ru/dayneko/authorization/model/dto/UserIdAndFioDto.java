package ru.dayneko.authorization.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
final public class UserIdAndFioDto {
    private long id;
    private String fio;
}
