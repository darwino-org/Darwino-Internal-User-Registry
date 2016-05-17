/*!COPYRIGHT HEADER! - CONFIDENTIAL 
 *
 * Darwino Inc Confidential.
 *
 * (c) Copyright Darwino Inc 2014-2015.
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.     
 */

package biz.webgate.darwino.userregistry.j2ee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.security.acl.User;
import com.darwino.commons.security.acl.UserAuthenticator;
import com.darwino.commons.security.acl.UserException;
import com.darwino.commons.security.acl.UserProvider;
import com.darwino.commons.security.acl.UserService;
import com.darwino.commons.security.acl.impl.UserImpl;
import com.darwino.commons.util.StringUtil;
import com.darwino.commons.util.io.StreamUtil;
import com.darwino.j2ee.application.DarwinoJ2EEApplication;
import com.darwino.jsonstore.Session;

import biz.webgate.darwino.userregistry.dao.UserProfile;
import biz.webgate.darwino.userregistry.dao.UserProfileStorageServiceImpl;

/**
 * Create a user service for this app.
 * 
 * @author Christian Guedemann
 */
public class AppUserService implements UserService {

	private final static UserProvider internalUserProvider = new InternalUserProvider();
	private final static UserAuthenticator internalUserAuthenticator = new InternalUserAuthenticator();
	
	@Override
	public Map<String, User> findUsers(String[] ids) throws UserException {
		Map<String,User> users = new HashMap<String, User>();
		for (String id: ids) {
			User user = findUser(id);
			if (user != null) {
				users.put(user.getDn(),user);
			}
		}
		return users;
	}

	@Override
	public UserAuthenticator getAuthenticator(String arg0) throws UserException {
		if (StringUtil.isEmpty(arg0)) {
			return internalUserAuthenticator;
		}
		return null;
	}

	@Override
	public UserProvider getProvider(String arg0) throws UserException {
		if (StringUtil.isEmpty(arg0)) {
			return internalUserProvider;
		}
		return null;
	}


	@Override
	public User findUser(String id) throws UserException {
		String[] defGroup = {"Darwino"};
		UserProfile up;
		Session session = null;
		try {
			session = DarwinoJ2EEApplication.get().getLocalJsonDBServer().createSystemSession("");
			try {
				up = UserProfileStorageServiceImpl.getInstance().getUserProfileByEMail(id, session);
				if (up == null) {
					up = UserProfileStorageServiceImpl.getInstance().getUserProfileByUNID(id, session);
				}
				if (up == null) {
					return null;
				}
				System.out.println("BuildUser");
				return new UserImpl(up.getUnid(), up.getFirstName() + " " + up.getLastName(), defGroup , null);
			} catch (JsonException e) {
				e.printStackTrace();
			}
		} catch (JsonException e1) {
			e1.printStackTrace();
		} finally {
			StreamUtil.close(session);
		}
		return null;
	}

	@Override
	public User findUserByLoginId(String arg0) throws UserException {
		// TODO SBA: What does loginId mean? email & unid are checked above, is this the confirmationID?
		return null;
	}

	@Override
	public List<Map<String, Object>> query(String query, String[] attributes, int skip, int limit, Map<String, Object> options) throws UserException {
		return internalUserProvider.query(query, attributes, skip, limit, options);
	}

	@Override
	public List<Map<String, Object>> typeAhead(String query, String[] attributes, int skip, int limit, Map<String, Object> options) throws UserException {
		return internalUserProvider.typeAhead(query, attributes, skip, limit, options);
	}
}
