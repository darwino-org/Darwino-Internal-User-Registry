package biz.webgate.darwino.userregistrationservice.rest.action;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistrationservice.dao.UserProfile;
import biz.webgate.darwino.userregistrationservice.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistrationservice.rest.RequestResult;

public class ActivationAction extends AbstractRestAction {
	
	private static final String STATUS_CONFIRMED = "CONFIRMED";
	
	@Override
	public RequestResult executeAction(HttpServiceContext context) throws JsonException, IOException {
		String confirmationId = context.getQueryParameterString("id");
		UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getUserProfileByConfirmationId(confirmationId);
		if (userProfile != null) {
			userProfile.setStatus(STATUS_CONFIRMED);
			if (UserProfileStorageServiceImpl.getInstance().saveUserProfile(userProfile)) {
				return RequestResult.buildProfileAnswer(userProfile);
			}
			throwError(context, "An error occurd while activating your profile!", null);
		} else {
			throwError(context, "Profile not found!", null);
		}
		return RequestResult.buildFailedActivationAnswer();
	}

}
