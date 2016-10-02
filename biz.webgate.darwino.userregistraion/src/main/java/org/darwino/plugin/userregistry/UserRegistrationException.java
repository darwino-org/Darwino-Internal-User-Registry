package org.darwino.plugin.userregistry;

import java.util.Collections;
import java.util.List;

public class UserRegistrationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> messages = Collections.emptyList();
	private final boolean validationOnly;

	public static UserRegistrationException buildConfigurationError(String message) {
		return new UserRegistrationException(message);

	}

	public static UserRegistrationException buildSystemError(Throwable cause) {
		return new UserRegistrationException("System Error", cause, false);
	}

	public static UserRegistrationException buildValidationError(String message, List<String> messages) {
		return new UserRegistrationException(message, messages, true);
	}

	public UserRegistrationException(String message) {
		super(message);
		validationOnly = false;
	}

	public UserRegistrationException(String message, Throwable cause, boolean validationOnly) {
		super(message, cause);
		this.validationOnly = validationOnly;
	}

	public UserRegistrationException(String message, Throwable cause, List<String> messages, boolean validationOnly) {
		super(message, cause);
		this.messages = messages;
		this.validationOnly = validationOnly;
	}

	public UserRegistrationException(String message, List<String> messages, boolean validationOnly) {
		super(message);
		this.messages = messages;
		this.validationOnly = validationOnly;
	}

	public UserRegistrationException(Throwable cause, List<String> messages, boolean validationOnly) {
		super(cause);
		this.messages = messages;
		this.validationOnly = validationOnly;
	}

	public List<String> getMessages() {
		return messages;
	}

	public boolean validationOnly() {
		return validationOnly;
	}
}
