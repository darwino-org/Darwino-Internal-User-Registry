package biz.webgate.darwino.userregistraionservice.j2ee;

import java.util.List;
import java.util.Map;

import com.darwino.commons.security.acl.UserException;
import com.darwino.commons.security.acl.UserIdentityMapper;
import com.darwino.commons.security.acl.UserProvider;

public class CleverPackUserProvider implements UserProvider {

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserIdentityMapper getIdentityMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> query(String query, String[] attributes, int skip, int limit, Map<String, Object> options) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> typeAhead(String query, String[] attributes, int skip, int limit, Map<String, Object> options) throws UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAvailable() throws UserException {
		// TODO Auto-generated method stub
		return false;
	}



}
