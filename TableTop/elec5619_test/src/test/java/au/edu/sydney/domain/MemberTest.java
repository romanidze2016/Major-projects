package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class MemberTest {

	@Test
	public void testSetAndGetUsername() {
		Member member = new Member();
		assertNull(member.getUsername());
		member.setUsername("Paul");
		assertEquals("Paul", member.getUsername());
		assertNotNull(member.getUsername());
	}
	
	@Test
	public void testJoined() {
		Member member = new Member();
		member.setJoined(123456);
		assertEquals(123456, member.getJoined());
		assertNotNull(member.getJoined());
	}
	
	@Test
	public void testId() {
		Member member = new Member();
		member.setId(123456);
		assertEquals(123456, member.getId());
		assertNotNull(member.getId());
	}
	
	@Test
	public void testRoomId() {
		Member member = new Member();
		member.setRoomId(123456);
		assertEquals(123456, member.getRoomId());
		assertNotNull(member.getRoomId());
	}

}
