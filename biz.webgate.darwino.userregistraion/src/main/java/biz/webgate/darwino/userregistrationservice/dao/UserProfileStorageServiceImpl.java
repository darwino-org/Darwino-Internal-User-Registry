package biz.webgate.darwino.userregistrationservice.dao;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Cursor;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;
import com.darwino.jsonstore.Store;
import com.darwino.jsonstore.callback.CursorEntry;
import com.darwino.jsonstore.pojo.AbstractPojoStorageService;
import com.darwino.platform.DarwinoContext;

import biz.webgate.darwino.userregistrationservice.UserProfileStorageService;
import biz.webgate.darwino.userregistrationservice.setup.DbSetup;

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

	public static UserProfileStorageService getInstance() {
		return m_Service;
	}

	private UserProfileStorageServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#saveUserProfile(biz.webgate.darwino.userregistrationservice.dao.UserProfile)
	 */
	@Override
	public boolean saveUserProfile(UserProfile userProfile) throws JsonException {

		Database db = DbSetup.INSTNACE.getDatabase();
		saveObject(userProfile, db);
		return true;

	}

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#getUserProfileByEMail(java.lang.String, com.darwino.jsonstore.Session)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#userIsAlreadyRegistred(biz.webgate.darwino.userregistrationservice.dao.UserProfile)
	 */
	@Override
	public boolean userIsAlreadyRegistred(UserProfile userProfile) throws JsonException {
		UserProfile up = getUserProfileByEMail(userProfile.getEmail(), null);
		return up != null;
	}

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#getUserProfileByUNID(java.lang.String, com.darwino.jsonstore.Session)
	 */
	@Override
	public UserProfile getUserProfileByUNID(String unid, Session session) throws JsonException {
		Database db = DbSetup.INSTNACE.getDatabase(session);
		UserProfile userProfile = getObjectByUNID(unid, db);
		return userProfile;
	}

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#getMyProfile()
	 */
	@Override
	public UserProfile getMyProfile() throws JsonException {
		String userId = DarwinoContext.get().getUser().getDn();
		UserProfile userProfile = getUserProfileByUNID(userId, null);
		return userProfile;
	}

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#deleteUserProfile(biz.webgate.darwino.userregistrationservice.dao.UserProfile)
	 */
	@Override
	public boolean deleteUserProfile(UserProfile userProfile) {
		return true;
	}

	/* (non-Javadoc)
	 * @see biz.webgate.darwino.userregistrationservice.dao.UserProfileSS#getUserProfileByConfirmationId(java.lang.String)
	 */
	@Override
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
