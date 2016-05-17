package biz.webgate.darwino.userregistry.rest.action;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistry.dao.UserProfile;
import biz.webgate.darwino.userregistry.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistry.rest.RequestResult;

public class GetMyProfileAction extends AbstractRestAction {

	@Override
	public RequestResult executeAction(HttpServiceContext context) throws JsonException, IOException {
		UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getMyProfile();
		if (userProfile != null) {
			//processToJson(context, userProfile);
			return RequestResult.buildProfileAnswer(userProfile);
		} 
//		else {
//			throwError(context, "User profile not found!", null);
//		}
		return RequestResult.buildProfileNotFoundAnswer();
	}
}
