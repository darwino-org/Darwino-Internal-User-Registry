package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;

import biz.webgate.darwino.userregistraionservice.dao.UserProfile;
import biz.webgate.darwino.userregistraionservice.dao.UserProfileStorageServiceImpl;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class GetMyProfileAction extends AbstractRestAction {

	@Override
	public boolean executeAction(HttpServiceContext context) throws JsonException, IOException {
		UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getMyProfile();
		if (userProfile != null) {
			processToJson(context, userProfile);
			return true;
		} else {
			throwError(context, "User profile not found!", null);
		}
		return false;
	}
}
