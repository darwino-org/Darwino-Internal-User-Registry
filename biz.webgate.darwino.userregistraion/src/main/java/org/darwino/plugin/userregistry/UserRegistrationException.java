package org.darwino.plugin.userregistry;

import java.util.Collections;
import java.util.List;

public class UserRegistrationException extends Exception {

	public enum ErrorType {
		SYSTEM, CONFIGURATON, VALIDATION
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> messages = Collections.emptyList();
	private final ErrorType errorType;

	public static UserRegistrationException buildConfigurationError(String message) {
		return new UserRegistrationException(message, null, null, ErrorType.CONFIGURATON);

	}

	public static UserRegistrationException buildSystemError(Throwable cause) {
		return new UserRegistrationException("System Error", cause, null, ErrorType.SYSTEM);
	}

	public static UserRegistrationException buildValidationError(List<String> messages) {
		return new UserRegistrationException("Validation failed", null, messages, ErrorType.VALIDATION);
	}

	private UserRegistrationException(String message, Throwable cause, List<String> messages, ErrorType error) {
		super(message, cause);
		this.messages = messages;
		this.errorType = error;
	}

	public List<String> getMessages() {
		return messages;
	}

	public ErrorType getErrorType() {
		return errorType;
	}
}
