package biz.webgate.darwino.userregistraionservice.rest;

import java.io.IOException;
import java.util.List;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.binding.JsonPojoSerializer;
import com.darwino.commons.json.binding.annotations.JsonEntityScope;
import com.darwino.commons.json.serialization.JsonWriter;
import com.darwino.commons.model.PojoJsonIntrospectorAnotationImpl;
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
		System.out.println("PSF loaded");
	}

	public class UserProfileService extends HttpService {

		private static final String PARAMETER_ACTION = "action";

		@Override
		public void service(HttpServiceContext context) {
			String action = context.getQueryParameterString(PARAMETER_ACTION);
			try {
				ProfileActionBinding userProfileAction = ProfileActionBinding.valueOf(action.toUpperCase());
				System.out.println("action: " + action + " userProfileAction: " + userProfileAction);
				RequestResult result = userProfileAction.executeAction(context);
				processToJson(context, result);
			} catch (Exception ex){
				try {
					context.emitException(ex);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		protected void processToJson(HttpServiceContext context, Object object) throws JsonException, IOException {
			JsonWriter jsonWriter = context.getJsonWriter();
			JsonPojoSerializer jsonPojoSerializer = new JsonPojoSerializer( PojoJsonIntrospectorAnotationImpl.get());
			jsonPojoSerializer.process2JSON(jsonWriter, object, JsonEntityScope.WEB);
			jsonWriter.close();
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
