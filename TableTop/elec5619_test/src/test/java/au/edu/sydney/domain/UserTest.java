package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	
	@Test
	public void testSetAndGetUsername() {
		User user = new User();
		assertNull(user.getUsername());
		user.setUsername("Paul");
		assertEquals("Paul", user.getUsername());
		assertNotNull(user.getUsername());
	}
	
	@Test
	public void testSetAndGetEmail() {
		User user = new User();
		assertNull(user.getEmail());
		user.setEmail("paul@mail.com");
		assertEquals("paul@mail.com", user.getEmail());
		assertNotNull(user.getEmail());
	}
	
	@Test
	public void testSetAndGetLast() {
		User user = new User();
		assertNull(user.getLast());
		user.setLast("Ryan");
		assertEquals("Ryan", user.getLast());
		assertNotNull(user.getLast());
	}
	
	@Test
	public void testPassword() {
		User user = new User();
		assertNull(user.getPassword());
		user.setPassword("brown");
		assertFalse(user.isValidPassword());
		assertEquals("brown", user.getPassword());
		user.setPassword("brownies");
		assertTrue(user.isValidPassword());
		assertEquals("brownies", user.getPassword());
	}
	
	@Test
	public void testValidUser() {
		User user = new User();
		user.setPassword("fishfingers");
		user.setLast("Dickson");
		user.setFirst("Margie");
		user.setUsername("MargieLovesGames");
		user.setEmail("margo@mail.com");
		assertTrue(user.isValid());
	}
	
	@Test
	public void testSetAndGetFirst() {
		User user = new User();
		assertNull(user.getFirst());
		user.setFirst("Alex");
		assertEquals("Alex", user.getFirst());
		assertNotNull(user.getFirst());
	}

}
