package com.epam.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExcepionHandler {

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ExceptionResponce> handleQuestionException(UserException exception, WebRequest request) {
		ExceptionResponce exceptionResponse = new ExceptionResponce();
		exceptionResponse.setError(exception.getMessage());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
		exceptionResponse.setTimeStamp(LocalDate.now().toString());
		exceptionResponse.setPath(request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = JwtException.class)
	public ResponseEntity<ExceptionResponce> handleJwtException(JwtException exception, WebRequest request) {
		ExceptionResponce responce = new ExceptionResponce();
		responce.setError(exception.getMessage());
		responce.setStatus(HttpStatus.UNAUTHORIZED.name());
		responce.setTimeStamp(LocalDate.now().toString());
		responce.setPath(request.getDescription(true));
		return new ResponseEntity<ExceptionResponce>(responce, HttpStatus.UNAUTHORIZED);
	}

}
