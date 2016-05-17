package biz.webgate.darwino.userregistry.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.darwino.commons.json.binding.annotations.JsonEntity;
import com.darwino.commons.json.binding.annotations.JsonObject;

import biz.webgate.darwino.userregistry.dao.UserProfile;

@JsonObject(pojoObjectType = "requestResult")
public class RequestResult {

	@JsonEntity(jsonProperty = "status")
	private final String status;
	@JsonEntity(jsonProperty = "error")
	private final String error;
	@JsonEntity(jsonProperty = "messages")
	private final List<String> messages;
	@JsonEntity(jsonProperty = "trace")
	private final String trace;
	@JsonEntity(jsonProperty = "profile")
	private final UserProfile profile;

	private RequestResult(String status, String error, List<String> messages, String trace, UserProfile profile) {
		super();
		this.status = status;
		this.error = error;
		this.messages = messages;
		this.trace = trace;
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

	public UserProfile getProfile() {
		return profile;
	}

	public static RequestResult buildProfileAnswer(UserProfile profile) {
		return new RequestResult("ok", null, null, null, profile);
	}
	
	public static RequestResult buildFailedActivationAnswer(){
		return new RequestResult("error", "Activation failed", null, null, null);
	}
	
	public static RequestResult buildProfileNotFoundAnswer(){
		return new RequestResult("error", "Profile not found", null, null, null);
	}
	
	public static RequestResult buildFailedProfileSave(){
		return new RequestResult("error", "Profile Save Failed", null, null, null);
	}

	public static RequestResult buildProfileValidationAnswer(UserProfile profile, List<String> messages) {
		if (messages.isEmpty()) {
			return buildProfileAnswer(profile);
		} else {
			return new RequestResult("validationfailed", "Validation failed", messages, null, profile);
		}
	}

	public static RequestResult buildErrorAnswer(UserProfile profile, Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String trace = sw.toString();
		return new RequestResult("error", t.getMessage(), null, trace, profile);
	}
}
