package org.darwino.plugin.userregistry.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.darwino.plugin.userregistry.UserRegistrationException;
import org.darwino.plugin.userregistry.bo.UserProfile;

import com.darwino.commons.serialize.annotations.Serialize;
import com.darwino.commons.serialize.annotations.SerializeObject;

@SerializeObject(pojoObjectType = "requestResult")
public class RequestResult {

	@Serialize(name = "status")
	private final String status;
	@Serialize(name = "error")
	private final String error;
	@Serialize(name = "messages")
	private final List<String> messages;
	@Serialize(name = "trace")
	private final String trace;
	@Serialize(name = "action")
	private final String action;
	@Serialize(name = "profile")
	private final UserProfile profile;

	private RequestResult(String status, String error, List<String> messages, String trace, String action, UserProfile profile) {
		super();
		this.status = status;
		this.error = error;
		this.messages = messages;
		this.trace = trace;
		this.action = action;
		this.profile = profile;
	}

	public String getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public List<String> getMessages() {
		return messages;
	}

	public String getTrace() {
		return trace;
	}

	public String getAction() {
		return action;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public static RequestResult buildActionOKAnswer(String action) {
		return new RequestResult("OK", null, null, null, action, null);
	}

	public static RequestResult buildActionOKAnswer(String action, UserProfile profile) {
		return new RequestResult("OK", null, null, null, action, profile);
	}

	public static RequestResult buildActionFailed(UserRegistrationException ex, String action) {
		return new RequestResult(ex.getErrorType().name(), ex.getMessage(), ex.getMessages(), extractStrackTrace(ex), action, null);
	}

	private static String extractStrackTrace(Exception ex) {
		if (ex.getStackTrace() == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String trace = sw.toString();
		return trace;
	}
}
