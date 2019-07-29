package ru.dayneko.authorization.utils.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import ru.dayneko.authorization.services.UserService;

public class UserCheck extends CheckMiddleware {
    private UserService userService;

    public UserCheck(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean check(Jws<Claims> claimsJws) {
        boolean userIsExist = userService.getUserByLogin(claimsJws.getBody().getSubject()).isPresent();

        if (!userIsExist) return false;

        return checkNext(claimsJws);
    }
}
