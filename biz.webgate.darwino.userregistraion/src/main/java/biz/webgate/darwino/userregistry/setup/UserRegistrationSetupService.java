package biz.webgate.darwino.userregistry.setup;

import org.passay.PasswordValidator;

public interface UserRegistrationSetupService {

	public String getDatabaseName();

	public String getEMailConfirmationMailText();

	public String getPasswordResetMailText();

	public String getApplicationTitle();

	public String getRegistrationInstruction();

	public String getLinkToTermsAndConditions();

	public PasswordValidator getPasswordValidator();
}
