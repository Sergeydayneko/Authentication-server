package ru.dayneko.authorization.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Date;

/**
 * Модель ошибки для взаимодействия с клиентом.
 *
 * @author dayneko_si
 * @since 07.05.2019
 */
@Getter
final public class ErrorResponse {
	private final HttpStatus status;
	private final String errorMessage;
	private final ErrorCode errorCode;
	private final Date timestamp;

	private ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
		this.errorMessage = message;
		this.errorCode = errorCode;
		this.status = status;
		this.timestamp = new Date();
	}

	/**
	 * Получение ошибки
	 *
	 * @param message   Общее сообщение об ошибке
	 * @param errorCode Код ошибки
	 * @param status    Код статуса ответа HTTP
	 * @return Ошибка
	 */
	public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
		return new ErrorResponse(message, errorCode, status);
	}
}
