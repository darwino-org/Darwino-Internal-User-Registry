package biz.webgate.darwin.userregistration.testsuite;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.passay.PasswordData;

import biz.webgate.darwino.userregistrationservice.setup.UserRegistrationSetupService;
import biz.webgate.darwino.userregistrationservice.util.PasswordFactory;

public class PassWordFactoryTest {

	@Test
	public void checkPasswordQualityDefaultTest() {
		String passwordStrong = "hansMartin99cool";
		String passwordWeak = "hansmartin cool";
		PasswordData pw = new PasswordData(passwordStrong);
		pw.setUsername("christian.guedemann@webgate.biz");
		List<String> messages = PasswordFactory.INSTANCE.validatePasswordQuality(pw, PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
		assertTrue(messages.isEmpty());
		PasswordData pwWeak = new PasswordData(passwordWeak);
		pwWeak.setUsername("christian.guedemann@webgate.biz");
		messages = PasswordFactory.INSTANCE.validatePasswordQuality(pwWeak, PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
		assertFalse(messages.isEmpty());
		assertEquals(3, messages.size());
	}

	@Test
	public void checkUsageOfDefaultPasswordQualtityImplTest() {
		UserRegistrationSetupService setupService = EasyMock.createNiceMock(UserRegistrationSetupService.class);
		expect(setupService.getPasswordValidator()).andReturn(null);
		expect(setupService.getPasswordValidator()).andReturn(null);

		replay(setupService);
		String passwordStrong = "hansMartin99cool";
		String passwordWeak = "hansmartin cool";

		PasswordData pw = new PasswordData(passwordStrong);
		pw.setUsername("christian.guedemann@webgate.biz");

		PasswordData pwWeak = new PasswordData(passwordWeak);
		pwWeak.setUsername("christian.guedemann@webgate.biz");

		List<String> messages = PasswordFactory.INSTANCE.validatePasswordQuality(pw, setupService.getPasswordValidator());
		assertTrue(messages.isEmpty());
		messages = PasswordFactory.INSTANCE.validatePasswordQuality(pwWeak, setupService.getPasswordValidator());
		assertFalse(messages.isEmpty());
		assertEquals(3, messages.size());

	}

	@Test
	public void checkUserNameTest() {
		String passwordStrong = "christian.Guedemann@webgate.biz";
		PasswordData pw = new PasswordData(passwordStrong);
		pw.setUsername("christian.guedemann@webgate.biz");
		List<String> messages = PasswordFactory.INSTANCE.validatePasswordQuality(pw, PasswordFactory.DEFAULT_PASSWORD_VALIDATOR);
		assertFalse(messages.isEmpty());
	}
}
