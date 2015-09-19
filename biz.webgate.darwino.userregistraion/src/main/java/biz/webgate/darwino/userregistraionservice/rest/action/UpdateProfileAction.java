package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;

import biz.webgate.darwino.userregistraionservice.dao.UserProfile;
import biz.webgate.darwino.userregistraionservice.dao.UserProfileStorageService;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class UpdateProfileAction extends AbstractRestAction {

	@Override
	public boolean executeAction(HttpServiceContext context) throws JsonException, IOException {
		String id = context.getQueryParameterString("id");
		UserProfile userProfile = UserProfileStorageService.getInstance().getUserProfileByUNID(id, null);
		userProfile = (UserProfile)processFromJson(context, new UserProfile());
		try {
			if (userProfile.isValid()) {
				if (UserProfileStorageService.getInstance().saveUserProfile(userProfile)) {
					throwPass(context);
					return true;
				}
				throwError(context, "An error occurd while saving your profile!", null);
			}
		} catch (IllegalArgumentException e) {
			throwError(context, e.getMessage(), e);
		}
		return false;
	}

}
