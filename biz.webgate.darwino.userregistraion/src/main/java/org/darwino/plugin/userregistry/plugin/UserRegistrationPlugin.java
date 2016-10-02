package org.darwino.plugin.userregistry.plugin;

import java.util.List;

import org.darwino.plugin.userregistry.ProfileServiceFactory;
import org.darwino.plugin.userregistry.bean.UserRegistrationBean;
import org.darwino.plugin.userregistry.j2ee.AppUserService;

import com.darwino.commons.Platform;
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
			UserRegistrationBean uregBean = Platform.getManagedBeanUnchecked(UserRegistrationBean.BEAN_NAME);
			if (uregBean != null) {
				extensions.add(new ProfileServiceFactory());
			} else {
				Platform.logManager(getClass().getCanonicalName()).w("No {0} Bean registred!", UserRegistrationBean.BEAN_NAME);
			}
		}
		if (UserService.class == serviceClass) {
			System.out.println("Loading AppService");
			extensions.add(new AppUserService());
		}
	}
	
}
