package ru.dayneko.authorization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dayneko.authorization.model.entity.user.Action;

@Repository
public interface ActionRepository extends CrudRepository<Action, Long> {
}
