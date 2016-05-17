package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;

import biz.webgate.darwino.userregistraionservice.dao.UserProfile;
import biz.webgate.darwino.userregistraionservice.dao.UserProfileStorageServiceImpl;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class GetProfileAction extends AbstractRestAction{

	@Override
	public boolean executeAction(HttpServiceContext context) throws JsonException, IOException {
			String id = context.getQueryParameterString("id");
			UserProfile userProfile = UserProfileStorageServiceImpl.getInstance().getUserProfileByUNID(id, null);
			if (userProfile != null) {
				processToJson(context, userProfile);
				return true;
			} else {
				
				throwError(context, "Id " + id + " not found!", null);
			}
			return false;
	}

}
