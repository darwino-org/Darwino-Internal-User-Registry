package org.darwino.plugin.userregistry;

import java.util.List;

import org.darwino.plugin.userregistry.rest.UserRegistryRestService;

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
		super(".darwino-3rd-userregistry-api");
		System.out.println("PSF loaded");
	}

	@Override
	protected void createServicesBinders(List<RestServiceBinder> binders) {
		// ///////////////////////////////////////////////////////////////////////////////
		// INFORMATION
		binders.add(new RestServiceBinder("registration") {
			@Override
			public HttpService createService(HttpServiceContext context, String[] parts) {
				return new UserRegistryRestService();
			}
		});
	}
}
