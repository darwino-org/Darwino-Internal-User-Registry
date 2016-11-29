package org.darwino.plugin.userregistry.dao;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.darwino.plugin.userregistry.UserProfileStorageService;
import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.setup.DbSetup;

import com.darwino.commons.json.JsonArray;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.json.JsonObject;
import com.darwino.jsonstore.Cursor;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.Store;
import com.darwino.jsonstore.callback.CursorEntry;
import com.darwino.jsonstore.pojo.AbstractPojoStorageService;
import com.darwino.platform.DarwinoContext;

public class UserProfileStorageServiceImpl extends AbstractPojoStorageService<UserProfile> implements UserProfileStorageService {
	/**
	 * Userprofile Storage Service.
	 * 
	 * This is the place where I have to implement my storage.
	 * 
	 * @author Webgate - AWR
	 */

	// TODO: Register service to the platform, Check the UserService
	// registration.

	private final static UserProfileStorageService m_Service = new UserProfileStorageServiceImpl();

	private final static String[] SEARCH_ATTRIBUTES = { "firstname", "lastname", "email" };

	public static UserProfileStorageService getInstance() {
		return m_Service;
	}

	private UserProfileStorageServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.webgate.darwino.userregistry.dao.UserProfileSS#saveUserProfile(biz.
	 * webgate.darwino.userregistry.dao.UserProfile)
	 */
	@Override
	public boolean saveUserProfile(UserProfile userProfile) throws JsonException {

		Database db = DbSetup.INSTANCE.getDatabase();
		saveObject(userProfile, db);
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.webgate.darwino.userregistry.dao.UserProfileSS#getUserProfileByEMail(
	 * java.lang.String, com.darwino.jsonstore.Session)
	 */
	@Override
	public UserProfile getUserProfileByEMail(String eMail, Session session) throws JsonException {
		Database db = DbSetup.INSTANCE.getDatabase(session);
		Store store = db.getStore(DbSetup.UP_STORE);
		Cursor c = store.openCursor().query("{@email:'" + eMail + "'}").range(0, 10);
		CursorEntry ce = c.findOne();
		if (ce != null) {
			return getObjectByUNID(ce.getUnid(), db);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.webgate.darwino.userregistry.dao.UserProfileSS#userIsAlreadyRegistred
	 * (biz.webgate.darwino.userregistry.dao.UserProfile)
	 */
	@Override
	public boolean userIsAlreadyRegistred(UserProfile userProfile) throws JsonException {
		UserProfile up = getUserProfileByEMail(userProfile.getEmail(), null);
		return up != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.webgate.darwino.userregistry.dao.UserProfileSS#getUserProfileByUNID(
	 * java.lang.String, com.darwino.jsonstore.Session)
	 */
	@Override
	public UserProfile getUserProfileByUNID(String unid, Session session) throws JsonException {
		Database db = DbSetup.INSTANCE.getDatabase(session);
		UserProfile userProfile = getObjectByUNID(unid, db);
		return userProfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.webgate.darwino.userregistry.dao.UserProfileSS#getMyProfile()
	 */
	@Override
	public UserProfile getMyProfile() throws JsonException {
		String userId = DarwinoContext.get().getUser().getDn();
		UserProfile userProfile = getUserProfileByUNID(userId, null);
		return userProfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * biz.webgate.darwino.userregistry.dao.UserProfileSS#deleteUserProfile(biz.
	 * webgate.darwino.userregistry.dao.UserProfile)
	 */
	@Override
	public boolean deleteUserProfile(UserProfile userProfile) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see biz.webgate.darwino.userregistry.dao.UserProfileSS#
	 * getUserProfileByConfirmationId(java.lang.String)
	 */
	@Override
	public UserProfile getUserProfileByConfirmationId(String confirmationCodeId) throws JsonException, IOException {
		Database db = DbSetup.INSTANCE.getDatabase();
		JsonObject selection = new JsonObject();
		selection.put("confirmationnumber", confirmationCodeId);
		List<UserProfile> users = selectObject(db, selection.toJson(), null, 100);
		if (users.isEmpty()) {
			return null;
		} else {
			return users.get(0);
		}
	}

	@Override
	protected UserProfile createPlainObject() {
		return new UserProfile();
	}

	@Override
	public String getStoreName() {
		return DbSetup.UP_STORE;
	}

	@Override
	public List<UserProfile> findUserByQuery(String query, int skip, int limit) throws JsonException, IOException {
		String selection = buildQuery(query);
		Database db = DbSetup.INSTANCE.getDatabase();

		int maxResult = Math.max(skip, 0) + limit;
		List<UserProfile> result = selectObject(db, selection, null, maxResult);
		int todelete = Math.max(skip, 0);
		for (Iterator<UserProfile> it = result.iterator(); it.hasNext();) {
			it.next();
			if (todelete > 0) {
				todelete--;
				it.remove();
			}
		}
		return result;
	}

	private String buildQuery(String query) throws JsonException, IOException {
		JsonObject or = new JsonObject();
		JsonArray orValues = new JsonArray();
		for (String attr : SEARCH_ATTRIBUTES) {
			JsonObject json = new JsonObject();
			json.put("$contains_i", attr);
			orValues.put(json);
		}
		or.put("$or", orValues);
		JsonObject and = new JsonObject();
		JsonArray andArray = new JsonArray();
		JsonObject isActive = new JsonObject();
		isActive.put( "registrationstatus", "ACTIVE");
		andArray.add(or);
		andArray.add(isActive);
		and.put("$and", andArray);
		return and.toJson();
	}

}
