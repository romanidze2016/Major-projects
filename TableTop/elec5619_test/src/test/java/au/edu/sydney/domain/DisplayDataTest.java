package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayDataTest {

	@Test
	public void testGetandSetUsername(){
		String username = "JohnDoe";
		
		DisplayData displayData = new DisplayData();
		displayData.setUsername(username);
		
		assertNotNull(displayData.getUsername());
		assertEquals(displayData.getUsername(), username);
	}
	
	@Test
	public void testGetandSetFirst(){
		String first = "John";
		
		DisplayData displayData = new DisplayData();
		displayData.setFirst(first);
		
		assertNotNull(displayData.getFirst());
		assertEquals(displayData.getFirst(), first);
	}
	
	@Test
	public void testGetandSetLast(){
		String last = "Doe";
		
		DisplayData displayData = new DisplayData();
		displayData.setLast(last);
		
		assertNotNull(displayData.getLast());
		assertEquals(displayData.getLast(), last);
		
	}
	@Test
	public void testGetandSetString(){
		String string = "test";
		
		DisplayData displayData = new DisplayData();
		displayData.setString(string);
		
		assertNotNull(displayData.getString());
		assertEquals(displayData.getString(), string);
	}
	
	@Test
	public void testDisplayData(){
		String username = "JohnDoe";
		String first = "John";
		String last = "Doe";
		String string = "test";
		
		DisplayData displayData = new DisplayData();
		displayData.setUsername(username);
		displayData.setFirst(first);
		displayData.setLast(last);
		displayData.setString(string);
		
		assertNotNull(displayData.getUsername());
		assertNotNull(displayData.getFirst());
		assertNotNull(displayData.getLast());
		assertNotNull(displayData.getString());
		assertEquals(displayData.getUsername(), username);
		assertEquals(displayData.getFirst(), first);
		assertEquals(displayData.getLast(), last);
		assertEquals(displayData.getString(), string);
	}
	
	
}
