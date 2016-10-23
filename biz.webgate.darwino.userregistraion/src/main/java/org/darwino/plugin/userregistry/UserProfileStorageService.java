package org.darwino.plugin.userregistry;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.setup.DbSetup;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Database;
import com.darwino.jsonstore.Session;

public interface UserProfileStorageService {

	public abstract boolean saveUserProfile(UserProfile userProfile) throws JsonException;

	public abstract UserProfile getUserProfileByEMail(String eMail, Session session) throws JsonException;

	public abstract boolean userIsAlreadyRegistred(UserProfile userProfile) throws JsonException;

	public abstract UserProfile getUserProfileByUNID(String unid, Session session) throws JsonException;

	public abstract UserProfile getMyProfile() throws JsonException;

	public abstract boolean deleteUserProfile(UserProfile userProfile);

	public abstract UserProfile getUserProfileByConfirmationId(String confirmationCodeId) throws JsonException, IOException;

	List<UserProfile> findUserByQuery(String query, int skip, int limit) throws JsonException, IOException;

}