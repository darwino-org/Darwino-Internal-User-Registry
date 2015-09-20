package biz.webgate.darwino.userregistraionservice.database;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonUtil;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.meta._Database;
import com.darwino.jsonstore.meta._FieldQuery;
import com.darwino.jsonstore.meta._FtSearch;
import com.darwino.jsonstore.meta._Store;
import com.darwino.platform.DarwinoContext;

public enum DbSetup {
	INSTNACE;
	public static String UP_STORE = "UserProfileStore";

	private String databaseName;
	
	public void setupUserStore( _Database db) throws JsonException {
		this.databaseName = db.getId();
		_Store store = db.addStore(UP_STORE);
		store.setLabel("User Profiles");
		store.setFtSearchEnabled(true);
		store.addQueryField(new _FieldQuery("email", JsonUtil.TYPE_STRING, false, "{$lowerCase: {$path: 'email'}}"));

		// Search the whole document (all fields)
		_FtSearch ft = (_FtSearch) store.setFTSearch(new _FtSearch());
		ft.setFields("firstname", "lastname", "email");

	}
		
	public Database getDatabase() throws JsonException {
		return getDatabase(null);
	}
	
	public Database getDatabase(Session session) throws JsonException {
		Database db = null;
		if (session != null) {
			db = session.getDatabase(databaseName);
		} else {
			db = DarwinoContext.get().getSession().getDatabase(databaseName);
		}
		return db;

	}
}
