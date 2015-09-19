package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.services.HttpServiceContext;

public interface IRestAction {
	public boolean executeAction(HttpServiceContext context) throws JsonException, IOException;
}
