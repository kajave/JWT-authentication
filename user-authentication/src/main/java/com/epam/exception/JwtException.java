package com.epam.exception;

public class JwtException extends RuntimeException {

	private String message;

	public JwtException() {
	}

	public JwtException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
