package com.sagar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unautorized user exception")
public class UnauthorizeAccessException extends Exception {
	static final long serialVersionUID = -3387516993334229948L;

	public UnauthorizeAccessException(String message) {
		super(message);
	}

}
