package biz.webgate.darwino.userregistry.rest.action;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

import biz.webgate.darwino.userregistry.rest.RequestResult;

public interface IRestAction {
	public RequestResult executeAction(HttpServiceContext context) throws JsonException, IOException;
}
