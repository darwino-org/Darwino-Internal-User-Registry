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

package org.darwino.plugin.userregistry.j2ee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.darwino.plugin.userregistry.bean.UserRegistrationBean;
import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.dao.UserProfileStorageServiceImpl;
import org.darwino.plugin.userregistry.util.PasswordFactory;

import com.darwino.commons.Platform;
import com.darwino.commons.json.JsonException;
import com.darwino.commons.security.acl.User;
import com.darwino.commons.security.acl.UserAuthenticator;
import com.darwino.commons.security.acl.UserException;
import com.darwino.commons.security.acl.impl.UserImpl;
import com.darwino.commons.util.StringUtil;
import com.darwino.commons.util.io.StreamUtil;
import com.darwino.config.user.UserDir;
import com.darwino.j2ee.application.DarwinoJ2EEApplication;
import com.darwino.jsonstore.Session;

/**
 * Create a user service for this app.
 * 
 * @author Christian Guedemann
 */
public class UserRegistrationDir extends UserDir implements UserAuthenticator {

	private UserRegistrationBean uregBean;
	private String bean;

	public class InternalUser extends UserImpl {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private UserProfile profile;

		public InternalUser(UserProfile profile) {
			this.profile = profile;
			Map<String, Object> attr = _getAttributes();
			attr.put(InternalUser.ATTR_DN, profile.getUnid());
			attr.put(InternalUser.ATTR_CN, profile.getFirstName() + " " + profile.getLastName());
			attr.put(InternalUser.ATTR_EMAIL, profile.getEmail());
			if (profile.getGroups() != null) {
				setGroups(new HashSet<String>(profile.getGroups()));
			}
			if (profile.getRoles() != null) {
				setRoles(new HashSet<String>(profile.getRoles()));
			}
		}

		public UserProfile getRegistryUserProfile() {
			return profile;
		}
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public UserRegistrationBean getUserRegistration() {
		if (uregBean == null) {
			uregBean = Platform.getManagedBean(UserRegistrationBean.BEAN_NAME);
			if (uregBean == null) {
				throw new RuntimeException(StringUtil.format("UserRegistration bean {0} is not available", getBean()));
			}
		}
		return uregBean;
	}

	@Override
	public UserAuthenticator getAuthenticator() throws UserException {
		return this;
	}

	@Override
	protected UserImpl _findUser(String id) throws UserException {
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
				return new InternalUser(up);
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
	protected UserImpl _findUserByLoginId(String loginValue) throws UserException {
		if (StringUtil.isEmpty(loginValue)) {
			return null;
		}
		return _findUser(loginValue);
	}

	@Override
	public List<Map<String, Object>> query(String query, String[] attributes, int skip, int limit, Map<String, Object> options) throws UserException {
		List<UserProfile> profiles;
		try {
			profiles = UserProfileStorageServiceImpl.getInstance().findUserByQuery(query, skip, limit);
		} catch (JsonException e) {
			throw new UserException(e);
		} catch (IOException e) {
			throw new UserException(e);
		}
		String[] intAttributes = attributes;
		if (attributes == null || attributes.length == 0) {
			intAttributes = UserDir.DEFAULT_SEARCH_ATTRIBUTES;
		}
		return buildListMap(profiles, intAttributes);
	}

	private List<Map<String, Object>> buildListMap(List<UserProfile> profiles, String[] attributes) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (UserProfile profile : profiles) {
			Map<String, Object> values = new HashMap<String, Object>();
			InternalUser us = new InternalUser(profile);
			values.put(User.ATTR_DN, us.getDn());
			for (String attr : attributes) {
				values.put(attr, us.getAttribute(attr));
			}
			result.add(values);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> typeAhead(String query, String[] attributes, int skip, int limit, Map<String, Object> options) throws UserException {
		List<UserProfile> profiles;
		try {
			profiles = UserProfileStorageServiceImpl.getInstance().findUserByQuery(query, skip, limit);
		} catch (JsonException e) {
			throw new UserException(e);
		} catch (IOException e) {
			throw new UserException(e);
		}
		String[] intAttributes = attributes;
		if (attributes == null || attributes.length == 0) {
			intAttributes = UserDir.DEFAULT_SEARCH_ATTRIBUTES;
		}
		return buildListMap(profiles, intAttributes);
	}

	@Override
	public String authenticate(String userName, String password) throws UserException {
		Session session = null;
		try {
			session = DarwinoJ2EEApplication.get().getLocalJsonDBServer().createSystemSession("");
			try {
				UserProfile up = UserProfileStorageServiceImpl.getInstance().getUserProfileByEMail(userName, session);
				if (up == null) {
					up = UserProfileStorageServiceImpl.getInstance().getUserProfileByUNID(userName, session);
				}
				if (up == null) {
					return null;
				}
				if (PasswordFactory.INSTANCE.validatePassword(password, up.getPasswordHash())) {
					return up.getUnid();
				}
			} catch (JsonException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (JsonException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			StreamUtil.close(session);
		}
		return null;
	}
}
