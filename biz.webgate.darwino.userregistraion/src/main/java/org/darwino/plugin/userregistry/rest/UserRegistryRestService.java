package org.darwino.plugin.userregistry.rest;

import org.darwino.plugin.userregistry.rest.action.ActivateInvitedUserAction;
import org.darwino.plugin.userregistry.rest.action.ActivateNewPasswordAction;
import org.darwino.plugin.userregistry.rest.action.ActivationAction;
import org.darwino.plugin.userregistry.rest.action.AskForNewPasswordAction;
import org.darwino.plugin.userregistry.rest.action.ChangePasswordAction;
import org.darwino.plugin.userregistry.rest.action.InviteAction;
import org.darwino.plugin.userregistry.rest.action.RegisterAction;

import com.darwino.commons.services.binding.rest.AbstractActionRestServiceImpl;

public class UserRegistryRestService extends AbstractActionRestServiceImpl {

	@Override
	public void registerRestActionsAndMethods() {
		addAction(new ActivationAction());
		addAction(new AskForNewPasswordAction());
		addAction(new ActivateNewPasswordAction());
		addAction(new ActivateInvitedUserAction());
		addAction(new ChangePasswordAction());
		addAction(new InviteAction());
		addAction(new RegisterAction());
	}

}
