package biz.webgate.darwino.userregistraionservice.dao;

import java.util.UUID;

import biz.webgate.darwino.userregistraionservice.util.PasswordFactory;

import com.darwino.commons.json.binding.PojoBaseImpl;
import com.darwino.commons.json.binding.annotations.JsonEntity;
import com.darwino.commons.json.binding.annotations.JsonEntityScope;
import com.darwino.commons.json.binding.annotations.JsonObject;
import com.darwino.commons.util.StringUtil;

@JsonObject(pojoObjectType = "biz.webgate.darwino.userregistrationservice.dao.UserProfile")
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

	public boolean isValid() throws IllegalArgumentException {
		// TODO: call with a List of <Messages> which are filed
		// (darwino.commons)
		// validate firstname
		if (StringUtil.isEmpty(StringUtil.trim(firstName))) {
			throw new IllegalArgumentException("Firstname is empty!");
		}
		// validate lastname
		if (StringUtil.isEmpty(StringUtil.trim(lastName))) {
			throw new IllegalArgumentException("LastName is empty!");
		}
		// validate email
		if (StringUtil.isEmpty(StringUtil.trim(email))) {
			throw new IllegalArgumentException("Email is empty!");
		} else if (!email.matches(REGEX_EMAIL_ADDRESS_PATTERN)) {
			throw new IllegalArgumentException("Email format exception!");
		}
		//
		// TBD: trim will remove leading and trailing spaces. This will not be
		// expected for a password I think...
		// TODO: trim?
		if (StringUtil.isEmpty(password)) {
			throw new IllegalArgumentException("password is empty!");
		}
		// TBD: password format validation???
		// TODO: validate password format...
		// validate confirmation
		if (StringUtil.isEmpty(confirmation)) {
			throw new IllegalArgumentException("confirmation is empty!");
		} else if (!StringUtil.equals(confirmation, password)) {
			throw new IllegalArgumentException("Confirmation does not match with password!");
		}
		PasswordFactory.INSTANCE.validatePasswordQuality(password);
		return true;
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
