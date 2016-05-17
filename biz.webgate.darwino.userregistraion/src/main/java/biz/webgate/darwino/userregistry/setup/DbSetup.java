package biz.webgate.darwino.userregistry.setup;

import java.util.ArrayList;
import java.util.List;

import com.darwino.commons.Platform;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonUtil;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.meta._Database;
import com.darwino.jsonstore.meta._FieldQuery;
import com.darwino.jsonstore.meta._Store;
import com.darwino.platform.DarwinoContext;

public enum DbSetup {
	INSTANCE;
	public static String UP_STORE = "UserProfileStore";

	public void setupUserStore(_Database db) throws JsonException {
		_Store store = db.addStore(UP_STORE);
		store.setLabel("User Profiles");
		store.setFtSearchEnabled(true);
		store.addQueryField(new _FieldQuery("email", JsonUtil.TYPE_STRING, false, "{$lowerCase: {$path: 'email'}}"));

	}

	public Database getDatabase() throws JsonException {
		return getDatabase(null);
	}

	public Database getDatabase(Session session) throws JsonException {
		List<UserRegistrationSetupService> extensions = new ArrayList<UserRegistrationSetupService>();
		System.out.println("DC"+DarwinoContext.get());
		//System.out.println("APP:"+Platform.findExtensions());
		extensions = Platform.findExtensions(UserRegistrationSetupService.class);
		if (extensions.isEmpty()) {
			throw new JsonException(null, "No UserRegisterationSetupService registered.", this);
		}
		UserRegistrationSetupService setup = (UserRegistrationSetupService) extensions.get(0);
		Database db = null;
		if (session != null) {
			db = session.getDatabase(setup.getDatabaseName());
		} else {
			db = DarwinoContext.get().getSession().getDatabase(setup.getDatabaseName());
		}
		return db;

	}
}
