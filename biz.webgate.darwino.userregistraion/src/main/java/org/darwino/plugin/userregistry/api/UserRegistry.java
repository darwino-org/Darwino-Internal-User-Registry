package org.darwino.plugin.userregistry.api;

import java.util.List;

import org.darwino.plugin.userregistry.UserRegistrationException;
import org.darwino.plugin.userregistry.bo.UserProfile;
import org.passay.PasswordValidator;

import com.darwino.commons.services.HttpServiceContext;

public interface UserRegistry {

	public void registerUser(UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException;

	public void inviteUser(UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException;

	public void activateInvitedUser(String confirmationId, UserProfile up, HttpServiceContext context) throws UserRegistrationException;

	public UserProfile activateUser(String confirmationId, HttpServiceContext context) throws UserRegistrationException;

	public void changePassword(UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException;

	public void askForNewPassword(String eMail, HttpServiceContext context) throws UserRegistrationException;

	public void activateNewPassword(String confirmationId, UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException;

	public UserProfile getMyUser(HttpServiceContext context) throws UserRegistrationException;

	public UserProfile getUserProfileById(String id, HttpServiceContext context) throws UserRegistrationException;

	public UserProfile getUserProfileByEMail(String emailAddress, HttpServiceContext context) throws UserRegistrationException;

	public List<UserProfile> getAllUsers(HttpServiceContext context) throws UserRegistrationException;

	public void saveUserProfile(UserProfile profile, HttpServiceContext context) throws UserRegistrationException;

	public void registerPasswordValidator(PasswordValidator validator);
}
