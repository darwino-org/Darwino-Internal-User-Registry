package biz.webgate.darwino.userregistrationservice.actions;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import biz.webgate.darwino.userregistrationservice.UserProfileStorageService;
import biz.webgate.darwino.userregistrationservice.UserRegistrationException;
import biz.webgate.darwino.userregistrationservice.dao.UserProfile;
import biz.webgate.darwino.userregistrationservice.setup.UserRegistrationSetupService;
import biz.webgate.darwino.userregistrationservice.util.PasswordFactory;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.security.acl.User;

public class RegisterNewUser {

	private final UserProfileStorageService storageService;
	private final UserRegistrationSetupService setupService;

	public RegisterNewUser(UserProfileStorageService storageService, UserRegistrationSetupService setupService) {
		super();
		this.storageService = storageService;
		this.setupService = setupService;
	}

	public void registerNewUser(UserProfile up, User currentUser) throws UserRegistrationException {
		List<String> messages = up.validateUser(setupService.getPasswordValidator());
		try {
			if (messages.isEmpty()) {
				if (storageService.userIsAlreadyRegistred(up)) {
					throw new UserRegistrationException("A User with this e-mail adress is already registred.", messages, true);
				}
				String passwordHash = PasswordFactory.INSTANCE.generateStrongPasswordHash(up.getPassword());
				up.setPasswordHash(passwordHash);
				up.initUnid();
				storageService.saveUserProfile(up);
			} else {
				throw new UserRegistrationException("Please check the following values", messages, true);
			}
		} catch (JsonException e) {
			throw new UserRegistrationException(e, null, false);
		} catch (NoSuchAlgorithmException e) {
			throw new UserRegistrationException(e, null, false);
		} catch (InvalidKeySpecException e) {
			throw new UserRegistrationException(e, null, false);
		}
	}
}
