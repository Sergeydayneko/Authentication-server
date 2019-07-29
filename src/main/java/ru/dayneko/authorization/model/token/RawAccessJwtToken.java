package ru.dayneko.authorization.model.token;


import io.jsonwebtoken.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Необработанное представление токена
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
@Data
@RequiredArgsConstructor
public class RawAccessJwtToken implements JwtToken {
	private String token;
	private Jws<Claims> claims;

	public RawAccessJwtToken(String token) {
		this.token = token;
	}
}
