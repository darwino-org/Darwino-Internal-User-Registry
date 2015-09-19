package biz.webgate.darwino.userregistraionservice.rest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonObject;
import com.darwino.commons.json.binding.JsonPojoDeserializer;
import com.darwino.commons.json.binding.JsonPojoSerializer;
import com.darwino.commons.json.binding.annotations.JsonEntityScope;
import com.darwino.commons.json.serialization.JsonWriter;
import com.darwino.commons.model.PojoJsonIntrospectorAnotationImpl;
import com.darwino.commons.services.HttpServiceContext;

public abstract class AbstractRestAction implements IRestAction {

	private static final String PARAMETER_STATUS = "status";
	private static final String STATUS_OK = "OK";
	private static final String STATUS_ERROR = "ERROR";
	private static final String STATUS_MESSAGE = "message";
	private static final String STATUS_STACKTRACE = "stacktrace";

	protected void processToJson(HttpServiceContext context, Object object) throws JsonException, IOException {
		JsonWriter jsonWriter = context.getJsonWriter();
		JsonPojoSerializer jsonPojoSerializer = new JsonPojoSerializer(new PojoJsonIntrospectorAnotationImpl());
		jsonPojoSerializer.process2JSON(jsonWriter, object, JsonEntityScope.WEB);
		jsonWriter.close();
	}

	protected Object processFromJson(HttpServiceContext context, Object targetObject) throws JsonException {
		JsonObject jsonObject = (JsonObject) context.getContentAsJson();
		JsonPojoDeserializer jsonPojoDeserializer = new JsonPojoDeserializer(new PojoJsonIntrospectorAnotationImpl());
		jsonPojoDeserializer.processJson2Object(jsonObject, targetObject, JsonEntityScope.WEB);
		return targetObject;
	}

	protected void throwPass(HttpServiceContext context) {
		JsonObject result = new JsonObject();
		result.put(PARAMETER_STATUS, STATUS_OK);
		context.emitJson(result);
	}

	public static void throwError(HttpServiceContext context, String errorMessage, Throwable t) {
		JsonObject result = new JsonObject();
		result.put(PARAMETER_STATUS, STATUS_ERROR);
		result.put(STATUS_MESSAGE, errorMessage);
		result.put(STATUS_STACKTRACE, getStackTrace(t));
		context.emitJson(result);
	}

	private static String getStackTrace(Throwable t) {
		if (t != null) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			try {
				t.printStackTrace(printWriter);
				String stackTrace = stringWriter.toString();
				printWriter.close();
				stringWriter.close();
				return stackTrace;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
