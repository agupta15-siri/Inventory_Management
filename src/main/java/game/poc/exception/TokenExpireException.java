package game.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TokenExpireException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TokenExpireException(String message) {
		super(message);
	}
}
