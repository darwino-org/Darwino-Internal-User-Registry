package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;

import biz.webgate.darwino.userregistraionservice.dao.UserProfile;
import biz.webgate.darwino.userregistraionservice.dao.UserProfileStorageService;
import biz.webgate.darwino.userregistraionservice.rest.RequestResult;
import biz.webgate.darwino.userregistraionservice.util.PasswordFactory;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class RegisterAction extends AbstractRestAction {

	@Override
	public RequestResult executeAction(HttpServiceContext context) {
		UserProfile userProfile = (UserProfile) processFromJson(context, new UserProfile());
		try {
			if (userProfile.isValid()) {
				if (UserProfileStorageService.getInstance().userIsAlreadyRegistred(userProfile)) {
					return RequestResult.buildErrorAnswer(userProfile, new IllegalAccessException("A User with this e-mail adress is already registred."));
				}
				String passwordHash = PasswordFactory.INSTANCE.generateStorngPasswordHash(userProfile.getPassword());
				userProfile.setPasswordHash(passwordHash);
				userProfile.initUnid();
				UserProfileStorageService.getInstance().saveUserProfile(userProfile);
				return RequestResult.buildProfileAnswer(null);
			}
		} catch (IllegalArgumentException e) {
			throwError(context, e.getMessage(), e);
		} catch (Exception ex) {
			throwError(context, "General Error!", ex);
		}
		return false;
	}
}
