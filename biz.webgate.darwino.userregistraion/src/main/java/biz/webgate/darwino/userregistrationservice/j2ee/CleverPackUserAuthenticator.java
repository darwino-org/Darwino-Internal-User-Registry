package biz.webgate.darwino.userregistrationservice.j2ee;

import com.darwino.commons.json.JsonException;
import com.darwino.commons.security.acl.UserAuthenticator;
import com.darwino.commons.security.acl.UserException;
import com.darwino.commons.util.io.StreamUtil;
import com.darwino.j2ee.application.DarwinoJ2EEApplication;
import com.darwino.jsonstore.Session;

import biz.webgate.darwino.userregistrationservice.dao.UserProfile;
import biz.webgate.darwino.userregistrationservice.dao.UserProfileStorageServiceImpl;
import biz.webgate.darwino.userregistrationservice.util.PasswordFactory;

public class CleverPackUserAuthenticator implements UserAuthenticator {

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
				System.out.println("USER FOUND");
				if( PasswordFactory.INSTANCE.validatePassword(password, up.getPasswordHash())) {
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
