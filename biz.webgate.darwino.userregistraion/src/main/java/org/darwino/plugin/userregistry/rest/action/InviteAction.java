package org.darwino.plugin.userregistry.rest.action;

import org.darwino.plugin.userregistry.UserRegistrationException;
import org.darwino.plugin.userregistry.bean.UserRegistrationBean;
import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.rest.RequestResult;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public class InviteAction extends AbstractUserRegistryAction {

	public InviteAction() {
		super("invite", new String[] { "GET" });
	}

	@Override
	protected RequestResult executeSpecificAction(HttpServiceContext context, UserRegistrationBean bean) throws JsonException, UserRegistrationException {
		UserProfile userProfile = new UserProfile(); 
		processFromJson(context, userProfile);
		bean.getUserRegistryAPI().inviteUser(userProfile, context);
		return RequestResult.buildActionOKAnswer(getActionName());
	}

}
