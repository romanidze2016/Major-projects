package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipTest {
	
	@Test
	public void testFriendShip() {
		User user1 = setUser1();
		User user2 = setUser2();
		
		Friendship friendship = new Friendship();
		friendship.setSender(user1.getUsername());
		friendship.setReceiver(user2.getUsername());
		
		assertEquals(friendship.getSender(), user1.getUsername());
		assertEquals(friendship.getReceiver(), user2.getUsername());
		assertNotNull(friendship.getId());
	}
	
	@Test
	public void testDefaultStatus(){
		User user1 = setUser1();
		User user2 = setUser2();
		
		Friendship friendship = new Friendship();
		friendship.setSender(user1.getUsername());
		friendship.setReceiver(user2.getUsername());
		
		assertNotNull(friendship.getId());
		assertEquals(friendship.getSender(), user1.getUsername());
		assertEquals(friendship.getReceiver(), user2.getUsername());
		assertEquals(friendship.getStatus(), 0);
	}
	
	@Test
	public void testSetStatus(){
		User user1 = setUser1();
		User user2 = setUser2();
		
		Friendship friendship = new Friendship();
		friendship.setSender(user1.getUsername());
		friendship.setReceiver(user2.getUsername());
		friendship.setStatus(1);
		
		assertNotNull(friendship.getId());
		assertEquals(friendship.getSender(), user1.getUsername());
		assertEquals(friendship.getReceiver(), user2.getUsername());
		assertEquals(friendship.getStatus(), 1);
	}
	
	public User setUser1(){
		User user = new User();
		user.setEmail("johndoe@email.com");
		user.setFirst("John");
		user.setLast("Doe");
		user.setUsername("JohnDoe");
		user.setPassword("password1");
	
		return user;
	}
	
	public User setUser2(){
		User user = new User();
		user.setEmail("janedoe@email.com");
		user.setFirst("Jane");
		user.setLast("Doe");
		user.setUsername("JaneDoe");
		user.setPassword("password2");
		
		return user;
	}
}