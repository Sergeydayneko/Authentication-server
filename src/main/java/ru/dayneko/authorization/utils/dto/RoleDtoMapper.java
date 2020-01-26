package ru.dayneko.authorization.utils.dto;

import ru.dayneko.authorization.model.dto.RoleDto;
import ru.dayneko.authorization.model.dto.RoleIdAndNameDto;
import ru.dayneko.authorization.model.entity.Role;

/**
 * Мэппер DTO для ролей
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
public interface RoleDtoMapper {
    RoleDto roleDto(Role role);
    RoleIdAndNameDto roleIdAndNameDto(Role role);
}
