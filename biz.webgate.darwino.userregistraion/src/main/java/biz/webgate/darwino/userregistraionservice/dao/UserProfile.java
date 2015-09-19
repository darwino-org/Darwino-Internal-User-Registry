package biz.webgate.darwino.userregistraionservice.dao;

import java.util.UUID;

import biz.webgate.darwino.userregistraionservice.util.PasswordFactory;

import com.darwino.commons.json.binding.PojoBaseImpl;
import com.darwino.commons.json.binding.annotations.JsonEntity;
import com.darwino.commons.json.binding.annotations.JsonEntityScope;
import com.darwino.commons.json.binding.annotations.JsonObject;
import com.darwino.commons.util.StringUtil;

@JsonObject(pojoObjectType = "com.cleverpack.app.users.UserProfile",javaFieldPrefix="m_" )
public class UserProfile extends PojoBaseImpl {

	private static final String STATUS_NOTCONFIRMED = "NOTCONFIRMED";

	private static final String REGEX_EMAIL_ADDRESS_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@JsonEntity(jsonProperty = "firstname")
	private String m_FirstName;
	@JsonEntity(jsonProperty = "lastname")
	private String m_LastName;
	@JsonEntity(jsonProperty = "email")
	private String m_Email;
	@JsonEntity(jsonProperty = "password", scope = JsonEntityScope.WEB)
	private String m_Password;
	@JsonEntity(jsonProperty = "passwordHash", scope = JsonEntityScope.STORE)
	private String m_PasswordHash;
	@JsonEntity(jsonProperty = "confirmation", scope = JsonEntityScope.WEB)
	private String m_Confirmation;
	@JsonEntity(jsonProperty = "accept")
	private boolean m_Accept;
	private String m_ConfirmationNumber;
	private String m_Status = STATUS_NOTCONFIRMED;
	@JsonEntity(jsonProperty = "openQuestionCount")
	private int m_OpenQuestionCount;
	@JsonEntity(jsonProperty = "answeredQuestionCount")
	private int m_AnsweredQuestionCount;
	@JsonEntity(jsonProperty = "answersCount")
	private int m_AnswersCount;
	@JsonEntity(jsonProperty = "sharedLinkCount")
	private int m_SharedLinkCount;
	@JsonEntity(jsonProperty = "publicationCount")
	private int m_PublicationCount;

	public String getFirstName() {
		return m_FirstName;
	}

	public void setFirstName(String firstName) {
		m_FirstName = firstName;
	}

	public String getLastName() {
		return m_LastName;
	}

	public void setLastName(String lastName) {
		m_LastName = lastName;
	}

	public String getEmail() {
		return m_Email == null ? null : m_Email.toLowerCase().trim();
	}

	public void setEmail(String email) {
		m_Email = email;
	}

	public String getPassword() {
		return m_Password;
	}

	public void setPassword(String password) {
		m_Password = password;
	}

	public String getConfirmation() {
		return m_Confirmation;
	}

	public void setConfirmation(String confirmation) {
		m_Confirmation = confirmation;
	}

	public boolean getAccept() {
		return m_Accept;
	}

	public void setAccept(boolean accept) {
		m_Accept = accept;
	}

	public void buildConfirmationNumber() {
		m_ConfirmationNumber = UUID.randomUUID().toString();
	}

	public String getConfirmationNumber() {
		return m_ConfirmationNumber;
	}

	public String getStatus() {
		return m_Status;
	}

	public void setStatus(String status) {
		m_Status = status;
	}

	public int getOpenQuestionCount() {
		return m_OpenQuestionCount;
	}

	public void setOpenQuestionCount(int openQuestionCount) {
		m_OpenQuestionCount = openQuestionCount;
	}

	public int getAnsweredQuestionCount() {
		return m_AnsweredQuestionCount;
	}

	public void setAnsweredQuestionCount(int answeredQuestionCount) {
		m_AnsweredQuestionCount = answeredQuestionCount;
	}

	public int getAnswersCount() {
		return m_AnswersCount;
	}

	public void setAnswersCount(int answersCount) {
		m_AnswersCount = answersCount;
	}

	public int getSharedLinkCount() {
		return m_SharedLinkCount;
	}

	public void setSharedLinkCount(int sharedLinkCount) {
		m_SharedLinkCount = sharedLinkCount;
	}

	public int getPublicationCount() {
		return m_PublicationCount;
	}

	public void setPublicationCount(int publicationCount) {
		m_PublicationCount = publicationCount;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		m_ConfirmationNumber = confirmationNumber;
	}

	public boolean isValid() throws IllegalArgumentException {
		//TODO: call with a List of <Messages> which are filed (darwino.commons)
		// validate firstname
		if (StringUtil.isEmpty(StringUtil.trim(m_FirstName))) {
			throw new IllegalArgumentException("Firstname is empty!");
		}
		// validate lastname
		if (StringUtil.isEmpty(StringUtil.trim(m_LastName))) {
			throw new IllegalArgumentException("LastName is empty!");
		}
		// validate email
		if (StringUtil.isEmpty(StringUtil.trim(m_Email))) {
			throw new IllegalArgumentException("Email is empty!");
		} else if (!m_Email.matches(REGEX_EMAIL_ADDRESS_PATTERN)) {
			throw new IllegalArgumentException("Email format exception!");
		}
		//
		// TBD: trim will remove leading and trailing spaces. This will not be
		// expected for a password I think...
		// TODO: trim?
		if (StringUtil.isEmpty(m_Password)) {
			throw new IllegalArgumentException("password is empty!");
		}
		// TBD: password format validation???
		// TODO: validate password format...
		// validate confirmation
		if (StringUtil.isEmpty(m_Confirmation)) {
			throw new IllegalArgumentException("confirmation is empty!");
		} else if (!StringUtil.equals(m_Confirmation, m_Password)) {
			throw new IllegalArgumentException("Confirmation does not match with password!");
		}
		PasswordFactory.INSTANCE.validatePasswordQuality(m_Password);
		return true;
	}

	public String getPasswordHash() {
		return m_PasswordHash;
	}

	public void setPasswordHash(String passwordHash) {
		m_PasswordHash = passwordHash;
	}

	@Override
	public String toString() {
		return "UserProfile [m_UNID=" + getUNID() + ", m_FirstName=" + m_FirstName + ", m_LastName=" + m_LastName + ", m_Email=" + m_Email + ", m_Confirmation=" + m_Confirmation + ", m_Accept="
				+ m_Accept + ", m_ConfirmationNumber=" + m_ConfirmationNumber + ", m_Status=" + m_Status + ", m_OpenQuestionCount=" + m_OpenQuestionCount + ", m_AnsweredQuestionCount="
				+ m_AnsweredQuestionCount + ", m_AnswersCount=" + m_AnswersCount + ", m_SharedLinkCount=" + m_SharedLinkCount + ", m_PublicationCount=" + m_PublicationCount + "]";
	}

}
