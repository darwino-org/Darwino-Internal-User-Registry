package org.darwino.plugin.userregistry.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.darwino.plugin.userregistry.util.PasswordFactory;
import org.passay.PasswordData;
import org.passay.PasswordValidator;

import com.darwino.commons.json.binding.PojoBaseImpl;
import com.darwino.commons.serialize.annotations.Serialize;
import com.darwino.commons.serialize.annotations.SerializeScope;
import com.darwino.commons.serialize.annotations.SerializeObject;
import com.darwino.commons.util.StringUtil;

@SerializeObject(pojoObjectType = "biz.webgate.darwino.userregistry.dao.UserProfile")
public class UserProfile extends PojoBaseImpl {

	public enum UserRegistrationStatus {
		ACTIVE, DEACTIVATED, INVITED, REGISTRED, ASK4PASSWORD, UNDEFINED
	};


	@Serialize(name = "firstname")
	private String firstName;
	@Serialize(name = "lastname")
	private String lastName;
	@Serialize(name = "email")
	private String email;
	@Serialize(name = "password", scope = SerializeScope.WEB)
	private String password;
	@Serialize(name = "passwordHash", scope = SerializeScope.STORE)
	private String passwordHash;
	@Serialize(name = "confirmation", scope = SerializeScope.WEB)
	private String confirmation;
	@Serialize(name = "oldpassword", scope = SerializeScope.WEB)
	private String oldpassword;
	@Serialize(name = "accept")
	private boolean accept;
	@Serialize(name = "confirmationnumber", scope = SerializeScope.STORE)
	private String confirmationNumber;
	@Serialize(name = "registrationstatus", scope = SerializeScope.STORE)
	private UserRegistrationStatus registrationStatus = UserRegistrationStatus.UNDEFINED;
	
	@Serialize(name = "properties", scope = SerializeScope.STORE)
	private Map<String,Object> properties;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email == null ? null : email.toLowerCase().trim();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public boolean getAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public void buildConfirmationNumber() {
		confirmationNumber = UUID.randomUUID().toString();
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public String toString() {
		return "UserProfile [m_UNID=" + getUnid() + ", m_FirstName=" + firstName + ", m_LastName=" + lastName + ", m_Email=" + email + ",  accept=" + accept + ", confirmationNumber="
				+ confirmationNumber + ", registrationStatus=" + registrationStatus + "]";
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public UserRegistrationStatus getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(UserRegistrationStatus registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

}
