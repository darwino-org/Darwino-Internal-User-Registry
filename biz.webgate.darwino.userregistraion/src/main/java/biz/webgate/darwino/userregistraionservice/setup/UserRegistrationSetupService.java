package biz.webgate.darwino.userregistraionservice.setup;

public interface UserRegistrationSetupService {

	public String getDatabaseName();

	public String getEMailConfirmationMailText();

	public String getPasswordResetMailText();

	public String getApplicationTitle();

	public String getRegistrationInstruction();

	public String getLinkToTermsAndConditions();
}
