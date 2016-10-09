package org.darwino.plugin.userregistry;

import java.io.IOException;

import org.darwino.plugin.userregistry.bo.UserProfile;

import com.darwino.commons.json.JsonException;
import com.darwino.jsonstore.Session;

public interface UserProfileStorageService {

	public abstract boolean saveUserProfile(UserProfile userProfile) throws JsonException;

	public abstract UserProfile getUserProfileByEMail(String eMail, Session session) throws JsonException;

	public abstract boolean userIsAlreadyRegistred(UserProfile userProfile) throws JsonException;

	public abstract UserProfile getUserProfileByUNID(String unid, Session session) throws JsonException;

	public abstract UserProfile getMyProfile() throws JsonException;

	public abstract boolean deleteUserProfile(UserProfile userProfile);

	public abstract UserProfile getUserProfileByConfirmationId(String confirmationCodeId) throws JsonException, IOException;

}