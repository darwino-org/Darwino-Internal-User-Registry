package biz.webgate.darwino.userregistraionservice.rest.action;

import java.util.Arrays;
import java.util.List;

import biz.webgate.darwino.userregistraionservice.dao.UserProfile;
import biz.webgate.darwino.userregistraionservice.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistraionservice.rest.RequestResult;
import biz.webgate.darwino.userregistraionservice.util.PasswordFactory;

import com.darwino.commons.services.HttpServiceContext;

public class RegisterAction extends AbstractRestAction {

	@Override
	public RequestResult executeAction(HttpServiceContext context) {
		try {
			UserProfile userProfile = (UserProfile) processFromJson(context, new UserProfile());
			List<String> messages = userProfile.validateUser(null);
			if (messages.isEmpty()) {
				if (UserProfileStorageServiceImpl.getInstance().userIsAlreadyRegistred(userProfile)) {
					return RequestResult.buildProfileValidationAnswer(userProfile, Arrays.asList("A User with this e-mail adress is already registred."));
				}
				String passwordHash = PasswordFactory.INSTANCE.generateStorngPasswordHash(userProfile.getPassword());
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
