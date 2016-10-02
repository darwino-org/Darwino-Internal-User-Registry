package org.darwino.plugin.userregistry.bean;

import org.darwino.plugin.userregistry.api.UserRegistry;
import org.darwino.plugin.userregistry.api.UserRegistryImpl;

import com.darwino.commons.Platform;
import com.darwino.commons.platform.beans.ManagedBean;

public class UserRegistrationBean implements ManagedBean {

	public static String BEAN_NAME = "dwo3rd/userregistry";

	private UserRegistry api;

	private String userAdminRole;
	private boolean selfRegistration = false;
	private boolean inviteRegistration = false;
	private String invitationRole;
	private String confirmationText;
	private String invitationText;
	private String passwordResetText;
	private String confimationUrl;
	private String invitationUrl;
	private String passwordResetUrl;

	@Override
	public void beanInitialize() {
		if (api == null) {
			Platform.log("Loading UserRegistraitonBean: API is null");
			api = new UserRegistryImpl(this);
		}
	}

	@Override
	public void beanDispose() {
	}

	public UserRegistry getUserRegistryAPI() {
		return api;
	}

	public String getUserAdminRole() {
		return userAdminRole;
	}

	public void setUserAdminRole(String userAdminRole) {
		this.userAdminRole = userAdminRole;
	}

	public boolean isSelfRegistration() {
		return selfRegistration;
	}

	public void setSelfRegistration(boolean selfRegistration) {
		this.selfRegistration = selfRegistration;
	}

	public boolean isInviteRegistration() {
		return inviteRegistration;
	}

	public void setInviteRegistration(boolean inviteRegistration) {
		this.inviteRegistration = inviteRegistration;
	}

	public String getInvitationRole() {
		return invitationRole;
	}

	public void setInvitationRole(String invitationRole) {
		this.invitationRole = invitationRole;
	}

	public String getConfirmationText() {
		return confirmationText;
	}

	public void setConfirmationText(String confirmationText) {
		this.confirmationText = confirmationText;
	}

	public String getInvitationText() {
		return invitationText;
	}

	public void setInvitationText(String invitationText) {
		this.invitationText = invitationText;
	}

	public String getPasswordResetText() {
		return passwordResetText;
	}

	public void setPasswordResetText(String passwordChangeText) {
		this.passwordResetText = passwordChangeText;
	}

	public String getConfimationUrl() {
		return confimationUrl;
	}

	public void setConfimationUrl(String confimationUrl) {
		this.confimationUrl = confimationUrl;
	}

	public String getInvitationUrl() {
		return invitationUrl;
	}

	public void setInvitationUrl(String invitationUrl) {
		this.invitationUrl = invitationUrl;
	}

	public String getPasswordResetUrl() {
		return passwordResetUrl;
	}

	public void setPasswordResetUrl(String passwordResetUrl) {
		this.passwordResetUrl = passwordResetUrl;
	}

}
