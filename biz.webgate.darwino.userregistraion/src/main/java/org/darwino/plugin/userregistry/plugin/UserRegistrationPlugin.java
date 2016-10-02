package org.darwino.plugin.userregistry.plugin;

import java.util.List;

import org.darwino.plugin.userregistry.ProfileServiceFactory;
import org.darwino.plugin.userregistry.j2ee.AppUserService;

import com.darwino.commons.platform.impl.PluginImpl;
import com.darwino.commons.security.acl.UserService;
import com.darwino.commons.services.HttpServiceFactory;

public class UserRegistrationPlugin extends PluginImpl{
	
	public UserRegistrationPlugin() {
		super("UserRegistry");
	}

	@Override
	public Object findDefaultService(Class<?> serviceClass) {
		return null;
	}

	@Override
	public void findExtensions(Class<?> serviceClass, List<Object> extensions) {
		if (HttpServiceFactory.class == serviceClass) {
			extensions.add(new ProfileServiceFactory());
		}
		if (UserService.class == serviceClass) {
			extensions.add(new AppUserService());
		}
	}
	
}
