package biz.webgate.darwino.userregistry.rest;

import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistry.rest.action.ActivationAction;
import biz.webgate.darwino.userregistry.rest.action.GetMyProfileAction;
import biz.webgate.darwino.userregistry.rest.action.GetProfileAction;
import biz.webgate.darwino.userregistry.rest.action.IRestAction;
import biz.webgate.darwino.userregistry.rest.action.RegisterAction;
import biz.webgate.darwino.userregistry.rest.action.UpdateProfileAction;

public enum ProfileActionBinding {
	GETMYPROFILE(new GetMyProfileAction()), GETPROFILE(new GetProfileAction()), UPDATEPROFILE(new UpdateProfileAction()), ACTIVATE(new ActivationAction()), REGISTER(new RegisterAction());

	private final IRestAction m_Action;

	private ProfileActionBinding(IRestAction action) {
		m_Action = action;
	}

	public RequestResult executeAction(HttpServiceContext context) {
		try {
			return m_Action.executeAction(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return RequestResult.buildErrorAnswer(null, e);
		}
	}
}
