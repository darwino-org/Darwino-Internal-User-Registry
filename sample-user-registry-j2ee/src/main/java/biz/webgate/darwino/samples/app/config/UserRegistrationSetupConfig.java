package biz.webgate.darwino.samples.app.config;

import org.passay.PasswordValidator;

import biz.webgate.darwino.samples.app.AppDatabaseDef;
import biz.webgate.darwino.userregistry.setup.UserRegistrationSetupService;

public class UserRegistrationSetupConfig implements UserRegistrationSetupService {

	@Override
	public String getDatabaseName() {
		return AppDatabaseDef.DATABASE_NAME;
	}

	@Override
	public String getEMailConfirmationMailText() {
		return "Email confirmation text";
	}

	@Override
	public String getPasswordResetMailText() {
		return "Email password reset text";
	}

	@Override
	public String getApplicationTitle() {
		return "SampeUserRegistry";
	}

	@Override
	public String getRegistrationInstruction() {
		return "Register yourself!";
	}

	@Override
	public String getLinkToTermsAndConditions() {
		return "www.google.com";
	}

	@Override
	public PasswordValidator getPasswordValidator() {
		return new PasswordValidator(null);
	}

}
