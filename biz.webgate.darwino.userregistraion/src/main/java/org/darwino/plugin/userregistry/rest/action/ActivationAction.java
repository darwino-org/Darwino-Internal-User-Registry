package org.darwino.plugin.userregistry.rest.action;

import org.darwino.plugin.userregistry.UserRegistrationException;
import org.darwino.plugin.userregistry.bean.UserRegistrationBean;
import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.rest.RequestResult;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class ActivationAction extends AbstractUserRegistryAction {

	public ActivationAction() {
		super("activate", new String[] { "GET" });
	}

	@Override
	protected RequestResult executeSpecificAction(HttpServiceContext context, UserRegistrationBean bean) throws JsonException, UserRegistrationException {
		String confirmationId = context.getQueryParameterString("id");
		UserProfile up = bean.getUserRegistryAPI().activateUser(confirmationId, context);
		return RequestResult.buildActionOKAnswer(getActionName(), up);

	}

}
