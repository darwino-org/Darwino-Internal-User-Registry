package org.darwino.plugin.userregistry.api;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

import org.darwino.plugin.userregistry.UserRegistrationException;
import org.darwino.plugin.userregistry.bean.UserRegistrationBean;
import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.bo.UserProfile.UserRegistrationStatus;
import org.darwino.plugin.userregistry.bo.UserProfileValidator;
import org.darwino.plugin.userregistry.dao.UserProfileStorageServiceImpl;
import org.darwino.plugin.userregistry.util.PasswordFactory;
import org.passay.PasswordValidator;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.util.StringUtil;

public class UserRegistryImpl implements UserRegistry {

	private final UserRegistrationBean userRegBean;
	private PasswordValidator validator = PasswordFactory.DEFAULT_PASSWORD_VALIDATOR;

	public UserRegistryImpl(UserRegistrationBean bean) {
		userRegBean = bean;
	}

	@Override
	public void registerUser(UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException {
		if (!userRegBean.isSelfRegistration()) {
			throw UserRegistrationException.buildConfigurationError("User self registration is not allowed!");
		}
		List<String> messages = UserProfileValidator.INSTNACE.validateUserRegistrationRequest(userProfile, validator);
		if (!messages.isEmpty()) {
			throw new UserRegistrationException("Validation error", messages, true);
		}
		try {
			if (UserProfileStorageServiceImpl.getInstance().userIsAlreadyRegistred(userProfile)) {
				throw UserRegistrationException.buildValidationError("A User with this e-mail address is already registred", Arrays.asList("A User with this e-mail address is already registred"));
			}
			userProfile.setRegistrationStatus(UserRegistrationStatus.REGISTRED);
			String passwordHash = PasswordFactory.INSTANCE.generateStrongPasswordHash(userProfile.getPassword());
			userProfile.setPasswordHash(passwordHash);
			userProfile.initUnid();
			UserProfileStorageServiceImpl.getInstance().saveUserProfile(userProfile);
		} catch (JsonException ex) {
			throw UserRegistrationException.buildSystemError(ex);

		} catch (NoSuchAlgorithmException e) {
			throw UserRegistrationException.buildSystemError(e);
		} catch (InvalidKeySpecException e) {
			throw UserRegistrationException.buildSystemError(e);
		}
	}

	@Override
	public void inviteUser(UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException {
		if (!userRegBean.isInviteRegistration()) {
			throw UserRegistrationException.buildConfigurationError("User invitation is not allowed!");
		}
		if (!StringUtil.isEmpty(userRegBean.getInvitationRole())) {
			if (!context.getUser().getRoles().contains(userRegBean.getInvitationRole())) {
				throw new UserRegistrationException("Access error", Arrays.asList("You are not allowed to invite a new User!"), true);
			}
		}
		List<String> messages = UserProfileValidator.INSTNACE.validateInvitation(userProfile);
		if (!messages.isEmpty()) {
			throw new UserRegistrationException("Validation error", messages, true);
		}
		try {
			if (UserProfileStorageServiceImpl.getInstance().userIsAlreadyRegistred(userProfile)) {
				throw UserRegistrationException.buildValidationError("A User with this e-mail address is already registred", Arrays.asList("A User with this e-mail address is already registred"));
			}
			userProfile.setRegistrationStatus(UserRegistrationStatus.INVITED);
			userProfile.initUnid();
			UserProfileStorageServiceImpl.getInstance().saveUserProfile(userProfile);
		} catch (JsonException ex) {
			throw UserRegistrationException.buildSystemError(ex);

		}

	}

	@Override
	public void activateUser(String confirmationId, HttpServiceContext context) throws UserRegistrationException {
		try {
			UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getUserProfileByConfirmationId(confirmationId);
			if (userProfile == null) {
				throw UserRegistrationException.buildValidationError("No conformation open", Arrays.asList("No Conformation open"));
			}
			if (userProfile.getRegistrationStatus() == UserRegistrationStatus.REGISTRED) {
				userProfile.setRegistrationStatus(UserRegistrationStatus.ACTIVE);
				userProfile.setConfirmationNumber("");
				UserProfileStorageServiceImpl.getInstance().saveUserProfile(userProfile);
			} else {
				throw UserRegistrationException.buildValidationError("User is not to activate", Arrays.asList("User is not to activate"));
			}
		} catch (JsonException ex) {
			throw UserRegistrationException.buildSystemError(ex);
		}

	}

	@Override
	public void changePassword(UserProfile profile, HttpServiceContext context) throws UserRegistrationException {
		try {
			UserProfile myUserProfile = getMyUser(context);
			List<String> messages = UserProfileValidator.INSTNACE.checkPasswordChange(profile, myUserProfile, validator);
			if (!messages.isEmpty()) {
				throw new UserRegistrationException("Validation error", messages, true);
			}
			String passwordHash = PasswordFactory.INSTANCE.generateStrongPasswordHash(profile.getPassword());
			myUserProfile.setPasswordHash(passwordHash);
			UserProfileStorageServiceImpl.getInstance().saveUserProfile(myUserProfile);
		} catch (JsonException e) {
			throw UserRegistrationException.buildSystemError(e);
		} catch (NoSuchAlgorithmException e) {
			throw UserRegistrationException.buildSystemError(e);
		} catch (InvalidKeySpecException e) {
			throw UserRegistrationException.buildSystemError(e);
		}

	}

	@Override
	public UserProfile getMyUser(HttpServiceContext context) throws UserRegistrationException {
		try {
			return UserProfileStorageServiceImpl.getInstance().getMyProfile();
		} catch (JsonException e) {
			throw UserRegistrationException.buildSystemError(e);
		}
	}

	@Override
	public UserProfile getUserProfileById(String id, HttpServiceContext context) throws UserRegistrationException {
		try {
			return UserProfileStorageServiceImpl.getInstance().getUserProfileByUNID(id,null);
		} catch (JsonException e) {
			throw UserRegistrationException.buildSystemError(e);
		}
	}

	@Override
	public UserProfile getUserProfileByEMail(String email, HttpServiceContext context) throws UserRegistrationException {
		try {
			return UserProfileStorageServiceImpl.getInstance().getUserProfileByEMail(email, null);
		} catch (JsonException e) {
			throw UserRegistrationException.buildSystemError(e);
		}
	}

	@Override
	public void saveUserProfile(UserProfile profile, HttpServiceContext context) throws UserRegistrationException {
	}

	@Override
	public void registerPasswordValidator(PasswordValidator validator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activateInvitedUser(String confirmationId, UserProfile up, HttpServiceContext context) throws UserRegistrationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void askForNewPassword(String eMail, HttpServiceContext context) throws UserRegistrationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void activateNewPassword(String confirmationId, UserProfile userProfile, HttpServiceContext context) throws UserRegistrationException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserProfile> getAllUsers(HttpServiceContext context) throws UserRegistrationException {
		// TODO Auto-generated method stub
		return null;
	}

}
