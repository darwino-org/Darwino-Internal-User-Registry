package biz.webgate.darwino.userregistry.rest.action;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistry.dao.UserProfile;
import biz.webgate.darwino.userregistry.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistry.rest.RequestResult;

public class GetProfileAction extends AbstractRestAction{

	@Override
	public RequestResult executeAction(HttpServiceContext context) throws JsonException, IOException {
			String id = context.getQueryParameterString("id");
			UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getUserProfileByUNID(id, null);
			if (userProfile != null) {
				//processToJson(context, userProfile);
				return RequestResult.buildProfileAnswer(userProfile);
			} 
//			else {
//				throwError(context, "Id " + id + " not found!", null);
//			}
			return RequestResult.buildProfileNotFoundAnswer();
	}

}
