package biz.webgate.darwino.userregistraionservice;

import java.util.List;

public class UserRegistrationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<String> messages;
	private final boolean validationOnly;

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
