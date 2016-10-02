package org.darwino.plugin.userregistry.bo;

import java.util.ArrayList;
import java.util.List;

import org.darwino.plugin.userregistry.util.PasswordFactory;
import org.passay.PasswordData;
import org.passay.PasswordValidator;

import com.darwino.commons.util.StringUtil;

public enum UserProfileValidator {
	INSTNACE;
	private static final String REGEX_EMAIL_ADDRESS_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public List<String> validateUserRegistrationRequest(UserProfile userProfile, PasswordValidator validator) {
		List<String> messages = new ArrayList<String>();
		if (StringUtil.isEmpty(StringUtil.trim(userProfile.getFirstName()))) {
			messages.add("Firstname is empty!");
		}
		// validate lastname
		if (StringUtil.isEmpty(StringUtil.trim(userProfile.getLastName()))) {
			messages.add("LastName is empty!");
		}
		// validate email
		if (StringUtil.isEmpty(StringUtil.trim(userProfile.getEmail()))) {
			messages.add("Email is empty!");
		} else if (!userProfile.getEmail().matches(REGEX_EMAIL_ADDRESS_PATTERN)) {
			messages.add("Email format exception!");
		}
		//
		if (StringUtil.isEmpty(userProfile.getPassword())) {
			messages.add("Password is empty!");
		}
		if (StringUtil.isEmpty(userProfile.getConfirmation())) {
			messages.add("confirmation is empty!");
		} else if (!StringUtil.equals(userProfile.getConfirmation(), userProfile.getPassword())) {
			messages.add("Confirmation does not match with password!");
		}
		if (messages.isEmpty()) {
			PasswordData pw = new PasswordData(userProfile.getPassword());
			pw.setUsername(userProfile.getEmail());
			messages =PasswordFactory.INSTANCE.validatePasswordQuality(pw, validator);
		}
		return messages;

	}

	public List<String> validateInvitation(UserProfile userProfile) {
		List<String> messages = new ArrayList<String>();
		if (StringUtil.isEmpty(StringUtil.trim(userProfile.getFirstName()))) {
			messages.add("Firstname is empty!");
		}
		// validate lastname
		if (StringUtil.isEmpty(StringUtil.trim(userProfile.getLastName()))) {
			messages.add("LastName is empty!");
		}
		// validate email
		if (StringUtil.isEmpty(StringUtil.trim(userProfile.getEmail()))) {
			messages.add("Email is empty!");
		} else if (!userProfile.getEmail().matches(REGEX_EMAIL_ADDRESS_PATTERN)) {
			messages.add("Email format exception!");
		}
		return messages;
	}

	public List<String> checkPasswordChange(UserProfile profile, UserProfile myUserProfile, PasswordValidator validator) {
		// TODO Auto-generated method stub
		return null;
	}

}
