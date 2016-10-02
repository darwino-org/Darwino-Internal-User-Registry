package biz.webgate.darwin.userregistration.testsuite;

import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import org.darwino.plugin.userregistry.bo.UserProfile;
import org.darwino.plugin.userregistry.bo.UserProfileValidator;
import org.darwino.plugin.userregistry.setup.UserRegistrationSetupService;
import org.darwino.plugin.userregistry.util.PasswordFactory;
import org.easymock.EasyMock;
import org.junit.Test;

public class UserProfileValidationTest {

	@Test
	public void testUserProfileValidation() {
		UserRegistrationSetupService setupService = EasyMock.createNiceMock(UserRegistrationSetupService.class);
		expect(setupService.getPasswordValidator()).andReturn(PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
		replay(setupService);

		UserProfile up = new UserProfile();
		up.setFirstName("firstName");
		up.setLastName("lastName");
		up.setEmail("email");
		up.setPassword("passW0rd");
		up.setConfirmation("passW0rd");
		up.setEmail("fn.ln@gmail.com");
		List<String> messages = UserProfileValidator.INSTNACE.validateUserRegistrationRequest(up,setupService.getPasswordValidator());
		assertTrue(messages.isEmpty());
	}
}
