package biz.webgate.darwino.userregistraionservice.dao;

import biz.webgate.darwino.userregistraionservice.setup.DbSetup;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Cursor;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.Store;
import com.darwino.jsonstore.callback.CursorEntry;
import com.darwino.jsonstore.pojo.AbstractPojoStorageService;
import com.darwino.platform.DarwinoContext;

public class UserProfileStorageService extends AbstractPojoStorageService<UserProfile> {
	/**
	 * Userprofile Storage Service.
	 * 
	 * This is the place where I have to implement my storage.
	 * 
	 * @author Webgate - AWR
	 */

	// TODO: Register service to the platform, Check the UserService
	// registration.

	private final static UserProfileStorageService m_Service = new UserProfileStorageService();

	public static UserProfileStorageService getInstance() {
		return m_Service;
	}

	private UserProfileStorageService() {
	}

	public boolean saveUserProfile(UserProfile userProfile) throws JsonException {

		Database db = DbSetup.INSTNACE.getDatabase();
		saveObject(userProfile, db);
		return true;

	}

	public UserProfile getUserProfileByEMail(String eMail, Session session) throws JsonException {
		Database db = DbSetup.INSTNACE.getDatabase(session);
		Store store = db.getStore(DbSetup.UP_STORE);
		Cursor c = store.openCursor().query("{@email:'" + eMail + "'}").range(0, 10);
		CursorEntry ce = c.findOne();
		if (ce != null) {
			return getObjectByUNID(ce.getUnid(), db);
		}
		return null;
	}

	public boolean userIsAlreadyRegistred(UserProfile userProfile) throws JsonException {
		UserProfile up = getUserProfileByEMail(userProfile.getEmail(), null);
		return up != null;
	}

	public UserProfile getUserProfileByUNID(String unid, Session session) throws JsonException {
		Database db = DbSetup.INSTNACE.getDatabase(session);
		UserProfile userProfile = getObjectByUNID(unid, db);
		return userProfile;
	}

	public UserProfile getMyProfile() throws JsonException {
		String userId = DarwinoContext.get().getUser().getDn();
		UserProfile userProfile = getUserProfileByUNID(userId, null);
		return userProfile;
	}

	public boolean deleteUserProfile(UserProfile userProfile) {
		return true;
	}

	public UserProfile getUserProfileByConfirmationId(String confirmationCodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected UserProfile createPlainObject() {
		return new UserProfile();
	}

	@Override
	public String getStoreName() {
		return DbSetup.UP_STORE;
	}

}
