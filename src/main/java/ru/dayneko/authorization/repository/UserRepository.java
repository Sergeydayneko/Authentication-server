package ru.dayneko.authorization.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dayneko.authorization.model.entity.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(long id);

    @Query(value = "SELECT CASE WHEN count(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.login = :username")
    boolean checkUsernameExistence(String username);
}
