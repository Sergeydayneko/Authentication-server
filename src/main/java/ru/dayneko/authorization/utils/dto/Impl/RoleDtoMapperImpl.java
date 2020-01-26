package ru.dayneko.authorization.utils.dto.Impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dayneko.authorization.model.dto.RoleDto;
import ru.dayneko.authorization.model.dto.RoleIdAndNameDto;
import ru.dayneko.authorization.model.entity.Role;
import ru.dayneko.authorization.utils.dto.RoleDtoMapper;

@AllArgsConstructor
@Service
final public class RoleDtoMapperImpl implements RoleDtoMapper {
    private final ModelMapper modelMapper;

    public RoleDto roleDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public RoleIdAndNameDto roleIdAndNameDto(Role role) {
        return modelMapper.map(role, RoleIdAndNameDto.class);
    }
}
