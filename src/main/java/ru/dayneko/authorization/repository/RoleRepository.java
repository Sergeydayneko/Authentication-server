package ru.dayneko.authorization.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dayneko.authorization.model.entity.Role;

import java.util.HashSet;
import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name IN (:roleNames)")
    List<Role> findAllRolesByName(@Param("roleNames") HashSet<String> roleNames);
}
