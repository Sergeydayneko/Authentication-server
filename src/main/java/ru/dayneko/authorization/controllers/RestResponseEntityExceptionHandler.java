package ru.dayneko.authorization.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.dayneko.authorization.exceptions.*;

/**
 * Перехватчик исключений в контроллере
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
@ControllerAdvice
public final class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UserNotFoundInDatabaseException.class, UsernameIsAlreadyExists.class} )
    protected ResponseEntity UserNotFoundHandler(RuntimeException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = {InvalidJwtTokenException.class, BlockedUserAuthorizationException.class})
    protected ResponseEntity InvalidJwtHandler(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity urlDoesntExistsExceptionHandler(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(404).body("Requested URL doesnt exists. Message: " + ex.getMessage());
    }

    @ExceptionHandler(value = {UserCredentialsAreNotValid.class})
    protected ResponseEntity userCredentialsAreNotValid(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(404).body("Check access error: " + ex.getMessage());
    }

}
