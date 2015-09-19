package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;

import biz.webgate.darwino.userregistraionservice.dao.UserProfile;
import biz.webgate.darwino.userregistraionservice.dao.UserProfileStorageService;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class ActivationAction extends AbstractRestAction {
	
	private static final String STATUS_CONFIRMED = "CONFIRMED";
	
	@Override
	public boolean executeAction(HttpServiceContext context) throws JsonException, IOException {
		String confirmationId = context.getQueryParameterString("id");
		UserProfile userProfile = UserProfileStorageService.getInstance().getUserProfileByConfirmationId(confirmationId);
		if (userProfile != null) {
			userProfile.setStatus(STATUS_CONFIRMED);
			if (UserProfileStorageService.getInstance().saveUserProfile(userProfile)) {
				return true;
			}
			throwError(context, "An error occurd while activating your profile!", null);
		} else {
			throwError(context, "Profile not found!", null);
		}
		return false;
	}

}
