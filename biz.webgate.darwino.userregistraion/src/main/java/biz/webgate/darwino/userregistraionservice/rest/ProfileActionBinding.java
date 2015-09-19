package biz.webgate.darwino.userregistraionservice.rest;

import java.io.IOException;

import biz.webgate.darwino.userregistraionservice.rest.action.ActivationAction;
import biz.webgate.darwino.userregistraionservice.rest.action.GetMyProfileAction;
import biz.webgate.darwino.userregistraionservice.rest.action.GetProfileAction;
import biz.webgate.darwino.userregistraionservice.rest.action.IRestAction;
import biz.webgate.darwino.userregistraionservice.rest.action.RegisterAction;
import biz.webgate.darwino.userregistraionservice.rest.action.UpdateProfileAction;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;
import com.darwino.commons.services.HttpServiceError;

public enum ProfileActionBinding {
	GETMYPROFILE(new GetMyProfileAction()), GETPROFILE(new GetProfileAction()), UPDATEPROFILE(new UpdateProfileAction()), ACTIVATE(new ActivationAction()), REGISTER(new RegisterAction());;

	private final IRestAction m_Action;

	private ProfileActionBinding(IRestAction action) {
		m_Action = action;
	}

	public boolean executeAction(HttpServiceContext context) {
		try {
			return m_Action.executeAction(context);
		} catch (JsonException e) {
			HttpServiceError.error500(e);
			e.printStackTrace();
		} catch (IOException e) {
			HttpServiceError.error500(e);
			e.printStackTrace();
		}
		return false;
	}
}
