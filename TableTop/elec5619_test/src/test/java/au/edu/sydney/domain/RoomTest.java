package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoomTest {

	@Test
	public void testMakeRoom(){
		Room room = new Room();
		
		assertNotNull(room.getId());
	}
	
	@Test
	public void testSetOwner(){
		Room room = new Room();
		String owner = "John";	
		
		room.setOwner(owner);
	
		assertEquals(room.getOwner(), owner);
	}
	
	@Test
	public void testGetStatus(){
		Room room = new Room();
		
		assertNotNull(room.getStatus());
		assertEquals(room.getStatus(), 0);
	}
	
}
