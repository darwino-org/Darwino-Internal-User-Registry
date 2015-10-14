package biz.webgate.darwino.userregistraionservice.rest.action;

import biz.webgate.darwino.userregistraionservice.rest.RequestResult;

import com.darwino.commons.services.HttpServiceContext;

public interface IRestAction {
	public RequestResult executeAction(HttpServiceContext context);
}
