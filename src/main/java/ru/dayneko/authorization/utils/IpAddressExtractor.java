package ru.dayneko.authorization.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Утильный класс для получения информации
 * по запросу
 *
 * @author dayneko_si
 * @since 27.05.2019
 */
@Service
@AllArgsConstructor
public class IpAddressExtractor {
    private final HttpServletRequest request;
    public String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
