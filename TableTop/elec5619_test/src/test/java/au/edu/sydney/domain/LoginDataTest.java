package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginDataTest {

	@Test
	public void testSetAndGetUsername() {
		LoginData loginData = new LoginData();
		assertNull(loginData.getUsername());
		loginData.setUsername("Paul");
		assertEquals("Paul", loginData.getUsername());
		assertNotNull(loginData.getUsername());
	}
	
	@Test
	public void testPassword() {
		LoginData loginData = new LoginData();
		assertNull(loginData.getPassword());
		loginData.setPassword("brownies");
		assertEquals("brownies", loginData.getPassword());
	}

}
