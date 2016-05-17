package biz.webgate.darwino.userregistry.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.passay.PasswordData;
import org.passay.PasswordValidator;

import com.darwino.commons.json.binding.PojoBaseImpl;
import com.darwino.commons.json.binding.annotations.JsonEntity;
import com.darwino.commons.json.binding.annotations.JsonEntityScope;
import com.darwino.commons.json.binding.annotations.JsonObject;
import com.darwino.commons.util.StringUtil;

import biz.webgate.darwino.userregistry.util.PasswordFactory;

@JsonObject(pojoObjectType = "biz.webgate.darwino.userregistry.dao.UserProfile")
public class UserProfile extends PojoBaseImpl {

	private static final String STATUS_NOTCONFIRMED = "NOTCONFIRMED";

	private static final String REGEX_EMAIL_ADDRESS_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@JsonEntity(jsonProperty = "firstname")
	private String firstName;
	@JsonEntity(jsonProperty = "lastname")
	private String lastName;
	@JsonEntity(jsonProperty = "email")
	private String email;
	@JsonEntity(jsonProperty = "password", scope = JsonEntityScope.WEB)
	private String password;
	@JsonEntity(jsonProperty = "passwordHash", scope = JsonEntityScope.STORE)
	private String passwordHash;
	@JsonEntity(jsonProperty = "confirmation", scope = JsonEntityScope.WEB)
	private String confirmation;
	@JsonEntity(jsonProperty = "accept")
	private boolean accept;
	private String confirmationNumber;
	private String status = STATUS_NOTCONFIRMED;

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

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> validateUser(PasswordValidator passwordValidator) throws IllegalArgumentException {
		List<String> messages = new ArrayList<String>();
		if (StringUtil.isEmpty(StringUtil.trim(firstName))) {
			messages.add("Firstname is empty!");
		}
		// validate lastname
		if (StringUtil.isEmpty(StringUtil.trim(lastName))) {
			messages.add("LastName is empty!");
		}
		// validate email
		if (StringUtil.isEmpty(StringUtil.trim(email))) {
			messages.add("Email is empty!");
		} else if (!email.matches(REGEX_EMAIL_ADDRESS_PATTERN)) {
			messages.add("Email format exception!");
		}
		//
		// TODO: trim?
		if (StringUtil.isEmpty(password)) {
			messages.add("Password is empty!");
		}
		// TODO: validate password format...
		if (StringUtil.isEmpty(confirmation)) {
			messages.add("confirmation is empty!");
		} else if (!StringUtil.equals(confirmation, password)) {
			messages.add("Confirmation does not match with password!");
		}
		if (messages.isEmpty()) {
			PasswordData pw = new PasswordData(password);
			pw.setUsername(email);
			PasswordFactory.INSTANCE.validatePasswordQuality(pw, passwordValidator);
		}
		return messages;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public String toString() {
		return "UserProfile [m_UNID=" + getUnid() + ", m_FirstName=" + firstName + ", m_LastName=" + lastName + ", m_Email=" + email + ", m_Confirmation=" + confirmation + ", m_Accept=" + accept
				+ ", m_ConfirmationNumber=" + confirmationNumber + ", m_Status=" + status + "]";
	}

}
