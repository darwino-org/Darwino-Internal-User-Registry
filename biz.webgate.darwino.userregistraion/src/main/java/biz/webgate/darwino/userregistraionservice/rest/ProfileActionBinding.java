package biz.webgate.darwino.userregistraionservice.rest;

import biz.webgate.darwino.userregistraionservice.rest.action.ActivationAction;
import biz.webgate.darwino.userregistraionservice.rest.action.GetMyProfileAction;
import biz.webgate.darwino.userregistraionservice.rest.action.GetProfileAction;
import biz.webgate.darwino.userregistraionservice.rest.action.IRestAction;
import biz.webgate.darwino.userregistraionservice.rest.action.RegisterAction;
import biz.webgate.darwino.userregistraionservice.rest.action.UpdateProfileAction;

import com.darwino.commons.services.HttpServiceContext;

public enum ProfileActionBinding {
	GETMYPROFILE(new GetMyProfileAction()), GETPROFILE(new GetProfileAction()), UPDATEPROFILE(new UpdateProfileAction()), ACTIVATE(new ActivationAction()), REGISTER(new RegisterAction());

	private final IRestAction m_Action;

	private ProfileActionBinding(IRestAction action) {
		m_Action = action;
	}

	public RequestResult executeAction(HttpServiceContext context) {
		return m_Action.executeAction(context);
	}
}
