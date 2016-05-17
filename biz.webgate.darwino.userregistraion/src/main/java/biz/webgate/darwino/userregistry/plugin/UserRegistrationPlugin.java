package biz.webgate.darwino.userregistry.plugin;

import java.util.List;

import com.darwino.commons.platform.Plugin;
import com.darwino.commons.security.acl.UserService;
import com.darwino.commons.services.HttpServiceFactory;
import com.darwino.commons.util.Version;

import biz.webgate.darwino.userregistry.j2ee.AppUserService;
import biz.webgate.darwino.userregistry.rest.ProfileServiceFactory;

public class UserRegistrationPlugin implements Plugin {

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

	@Override
	public String getName() {
		return "UserRegistrationClass";
	}

	@Override
	public Version getVersion() {
		return new Version(0, 8, 0);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
