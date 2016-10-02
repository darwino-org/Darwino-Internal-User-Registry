package org.darwino.plugin.userregistry.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.darwino.plugin.userregistry.UserRegistrationException;

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

	private RequestResult(String status, String error, List<String> messages, String trace, String action) {
		super();
		this.status = status;
		this.error = error;
		this.messages = messages;
		this.trace = trace;
		this.action = action;
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

	public static RequestResult buildActionOKAnswer(String action) {
		return new RequestResult("OK", null, null, null, action);
	}

	public static RequestResult buildActionFailed(UserRegistrationException ex, String action) {
		if (ex.validationOnly()) {
			return new RequestResult("validationfailed", ex.getMessage(), ex.getMessages(), null, action);
		} else {	
	
			return new RequestResult("error",ex.getMessage(), ex.getMessages(), extractStrackTrace(ex), action);
		}
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
