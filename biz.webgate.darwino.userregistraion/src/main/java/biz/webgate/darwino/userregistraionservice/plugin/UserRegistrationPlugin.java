package biz.webgate.darwino.userregistraionservice.plugin;

import java.util.List;

import biz.webgate.darwino.userregistraionservice.j2ee.AppUserService;
import biz.webgate.darwino.userregistraionservice.rest.ProfileServiceFactory;

import com.darwino.commons.platform.Plugin;
import com.darwino.commons.security.acl.UserService;
import com.darwino.commons.services.HttpServiceFactory;
import com.darwino.commons.util.Version;

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
		return "WGC UserRegistrationClass";
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
