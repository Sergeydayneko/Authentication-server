package ru.dayneko.authorization.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dayneko.authorization.model.dto.RoleDto;
import ru.dayneko.authorization.model.dto.RoleIdAndNameDto;
import ru.dayneko.authorization.services.RoleService;
import ru.dayneko.authorization.utils.dto.RoleDtoMapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

/**
 * Контроллер для получения данных по ролям
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
@Api(tags = "Контроллер для получения данных по ролям")
final public class RoleController {
    private final RoleService roleService;
    private final RoleDtoMapper roleDtoMapper;

    @ApiOperation("Получение краткого описания всех ролей пользователей")
    @GetMapping("/roles")
    public List<RoleIdAndNameDto> getAllRoles() {
        return roleService.getAllRoles()
                .stream()
                .map(roleDtoMapper::roleIdAndNameDto)
                .collect(toCollection(ArrayList::new));
    }

    @ApiOperation("Получение детализированного описания ролей пользователей")
    @GetMapping("/roles/detailed")
    public List<RoleDto> getAllDetailedRoles() {
        return roleService.getAllRoles()
                .stream()
                .map(roleDtoMapper::roleDto)
                .collect(toCollection(ArrayList::new));
    }
}
