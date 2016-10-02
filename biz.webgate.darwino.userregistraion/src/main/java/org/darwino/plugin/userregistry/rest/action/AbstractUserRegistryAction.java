package org.darwino.plugin.userregistry.rest.action;

import java.io.IOException;

import org.darwino.plugin.userregistry.UserRegistrationException;
import org.darwino.plugin.userregistry.bean.UserRegistrationBean;
import org.darwino.plugin.userregistry.rest.RequestResult;

import com.darwino.commons.Platform;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.services.HttpServiceError;
import com.darwino.commons.services.binding.rest.AbstractRestAction;

public abstract class AbstractUserRegistryAction extends AbstractRestAction {

	public AbstractUserRegistryAction(String actionName, String[] supportedMethods) {
		super(actionName, supportedMethods);
	}

	@Override
	public void executeAction(HttpServiceContext context) throws HttpServiceError {
		try {
			UserRegistrationBean bean = (UserRegistrationBean) Platform.getManagedBean(UserRegistrationBean.BEAN_NAME);
			if (bean == null) {
				throw HttpServiceError.error(500, "User registry is not configured");
			}
			processToJson(context, executeSpecificAction(context, bean));
		} catch (UserRegistrationException ex) {
			try {
				processToJson(context, RequestResult.buildActionFailed(ex, getActionName()));
			} catch (Exception ex2) {
				throw HttpServiceError.error(ex2, 500);
			}
		} catch (JsonException e) {
			throw HttpServiceError.error(e, 500);
		} catch (IOException e) {
			throw HttpServiceError.error(e, 500);
		}
	}

	protected abstract RequestResult executeSpecificAction(HttpServiceContext context, UserRegistrationBean bean) throws JsonException, UserRegistrationException;

}