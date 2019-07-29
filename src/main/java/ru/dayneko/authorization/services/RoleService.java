package ru.dayneko.authorization.services;

import ru.dayneko.authorization.model.entity.user.Role;

import java.util.List;

/**
 * Сервис для работы с ролями
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
public interface RoleService {
    List<Role> getAllRoles();
}
