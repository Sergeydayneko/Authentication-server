package ru.dayneko.authorization.utils.jwt.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public abstract class CheckMiddleware {
    private CheckMiddleware nextCheck;

    public CheckMiddleware linkWith(CheckMiddleware checkMiddleware) {
        this.nextCheck = checkMiddleware;
        return nextCheck;
    }

    public abstract boolean check(Jws<Claims> claimsJws);

    public boolean checkNext(Jws<Claims> claimsJws) {
        if (nextCheck == null) {
            return true;
        }
        return nextCheck.check(claimsJws);
    }
}
