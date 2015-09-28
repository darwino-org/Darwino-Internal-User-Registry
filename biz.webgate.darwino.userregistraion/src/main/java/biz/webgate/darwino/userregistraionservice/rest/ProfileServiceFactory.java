package biz.webgate.darwino.userregistraionservice.rest;

import java.util.List;

import biz.webgate.darwino.userregistraionservice.rest.action.AbstractRestAction;

import com.darwino.commons.services.HttpService;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.services.rest.RestServiceBinder;
import com.darwino.commons.services.rest.RestServiceFactory;

/**
 * Userprofile Service Factory.
 * 
 * This is the place where to define custom user profile services.
 * 
 * @author WebGate - AWR
 */

public class ProfileServiceFactory extends RestServiceFactory {

	
	public ProfileServiceFactory() {
		super("api/wgcprofileservice");
	}

	public class UserProfileService extends HttpService {

		private static final String PARAMETER_ACTION = "action";

		@Override
		public void service(HttpServiceContext context) {
			String action = context.getQueryParameterString(PARAMETER_ACTION);
			try {
				ProfileActionBinding userProfileAction = ProfileActionBinding.valueOf(action.toUpperCase());
				System.out.println("action: " + action + " userProfileAction: " + userProfileAction);
				userProfileAction.executeAction(context);
			} catch (IllegalArgumentException e) {
				AbstractRestAction.throwError(context, "IllegalArgumentException", e);
				//HttpServiceError.errorUnsupportedMethod(action);
				//&HttpServiceError.error500(e);
			}
		}
	}

	@Override
	protected void createServicesBinders(List<RestServiceBinder> binders) {
		// ///////////////////////////////////////////////////////////////////////////////
		// INFORMATION
		binders.add(new RestServiceBinder() {
			@Override
			public HttpService createService(HttpServiceContext context, String[] parts) {
				return new UserProfileService();
			}
		});
	}
}
