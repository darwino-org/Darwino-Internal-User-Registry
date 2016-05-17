package biz.webgate.darwino.userregistrationservice.rest.action;

import biz.webgate.darwino.userregistrationservice.rest.RequestResult;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public interface IRestAction {
	public RequestResult executeAction(HttpServiceContext context) throws JsonException, IOException;
}
