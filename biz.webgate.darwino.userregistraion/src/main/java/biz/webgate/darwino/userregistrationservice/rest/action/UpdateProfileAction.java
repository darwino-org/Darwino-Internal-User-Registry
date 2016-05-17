package biz.webgate.darwino.userregistrationservice.rest.action;

import java.io.IOException;
import java.util.List;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistrationservice.dao.UserProfile;
import biz.webgate.darwino.userregistrationservice.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistrationservice.rest.RequestResult;
import biz.webgate.darwino.userregistrationservice.util.PasswordFactory;

public class UpdateProfileAction extends AbstractRestAction {

	@Override
	public RequestResult executeAction(HttpServiceContext context) throws JsonException, IOException {
		String id = context.getQueryParameterString("id");
		UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getUserProfileByUNID(id, null);
		userProfile = (UserProfile)processFromJson(context, new UserProfile());
		try {
			List<String> messages = userProfile.validateUser(PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
			if (messages.size() < 1) {
				if (UserProfileStorageServiceImpl.getInstance().saveUserProfile(userProfile)) {
					//throwPass(context);
					return RequestResult.buildProfileAnswer(userProfile);
				}
				//throwError(context, "An error occurd while saving your profile!", null);
			} else {
				return RequestResult.buildProfileValidationAnswer(userProfile, messages);
			}
		} catch (IllegalArgumentException e) {
			//throwError(context, e.getMessage(), e);
			return RequestResult.buildErrorAnswer(userProfile, e);
		}
		return RequestResult.buildFailedProfileSave();
	}

}
