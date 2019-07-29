package ru.dayneko.authorization.services.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dayneko.authorization.repository.RoleRepository;
import ru.dayneko.authorization.services.RoleService;
import ru.dayneko.authorization.model.entity.user.Role;

import java.util.List;

@Service
@AllArgsConstructor
final public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

}
