package biz.webgate.darwino.userregistry.rest.action;

import java.util.Arrays;
import java.util.List;

import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistry.dao.UserProfile;
import biz.webgate.darwino.userregistry.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistry.rest.RequestResult;
import biz.webgate.darwino.userregistry.util.PasswordFactory;

public class RegisterAction extends AbstractRestAction {

	@Override
	public RequestResult executeAction(HttpServiceContext context) {
		try {
			UserProfile userProfile = (UserProfile) processFromJson(context, new UserProfile());
			List<String> messages = userProfile.validateUser(PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
			if (messages.isEmpty()) {
				if (UserProfileStorageServiceImpl.getInstance().userIsAlreadyRegistred(userProfile)) {
					return RequestResult.buildProfileValidationAnswer(userProfile, Arrays.asList("A User with this e-mail adress is already registred."));
				}
				String passwordHash = PasswordFactory.INSTANCE.generateStrongPasswordHash(userProfile.getPassword());
				userProfile.setPasswordHash(passwordHash);
				userProfile.initUnid();
				UserProfileStorageServiceImpl.getInstance().saveUserProfile(userProfile);
				return RequestResult.buildProfileAnswer(null);
			} else {
				return RequestResult.buildProfileValidationAnswer(userProfile, messages);
			}
		} catch (IllegalArgumentException e) {
			return RequestResult.buildErrorAnswer(null, e);
		} catch (Exception ex) {
			return RequestResult.buildErrorAnswer(null, ex);
		}
	}
}
