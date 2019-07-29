package ru.dayneko.authorization.utils.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Date;

public class DateCheck extends CheckMiddleware {
    @Override
    public boolean check(Jws<Claims> claimsJws) {
        if (new Date().before(claimsJws.getBody().getExpiration())) return false;

        return checkNext(claimsJws);
    }
}
