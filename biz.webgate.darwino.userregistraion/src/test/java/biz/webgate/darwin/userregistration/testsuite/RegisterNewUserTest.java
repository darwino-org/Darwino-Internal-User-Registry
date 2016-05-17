package biz.webgate.darwin.userregistration.testsuite;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;

import com.darwino.commons.json.JsonException;

import biz.webgate.darwino.userregistrationservice.UserProfileStorageService;
import biz.webgate.darwino.userregistrationservice.UserRegistrationException;
import biz.webgate.darwino.userregistrationservice.actions.RegisterNewUser;
import biz.webgate.darwino.userregistrationservice.dao.UserProfile;
import biz.webgate.darwino.userregistrationservice.setup.UserRegistrationSetupService;
import biz.webgate.darwino.userregistrationservice.util.PasswordFactory;

public class RegisterNewUserTest {

	@Test
	public void testRegisterNewUserFromRestServiceSuccess() throws JsonException {
		UserProfile up = new UserProfile();
		up.setFirstName("Franz");
		up.setLastName("Meier");
		up.setEmail("franz.meier@gmail.com");
		up.setPassword("ThisIsAS3curePassW0rd");
		up.setConfirmation("ThisIsAS3curePassW0rd");
		UserProfileStorageService storageService = EasyMock.createNiceMock(UserProfileStorageService.class);
		expect(storageService.userIsAlreadyRegistred(up)).andReturn(false);
		expect(storageService.saveUserProfile(up)).andReturn(true);
		replay(storageService);

		UserRegistrationSetupService setupService = EasyMock.createNiceMock(UserRegistrationSetupService.class);
		expect(setupService.getPasswordValidator()).andReturn(PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
		replay(setupService);
		try {
			RegisterNewUser reg = new RegisterNewUser(storageService, setupService);
			reg.registerNewUser(up, null);
		} catch (UserRegistrationException ex) {
			System.out.println(ex.getMessage());
			for (String msg : ex.getMessages()) {
				System.out.println(msg);
			}
			assertTrue(false);
		}
		verify(storageService);
		verify(setupService);
		assertTrue(true);
	}
}
