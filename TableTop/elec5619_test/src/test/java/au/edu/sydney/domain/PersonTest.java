package au.edu.sydney.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testSetAndGetId() {
		Person person = new Person();
		person.setId(123);
		assertEquals(123, person.getId());
		assertNotNull(person.getId());
	}
	
	@Test
	public void testSetAndGetLast() {
		Person person = new Person();
		assertNull(person.getLast());
		person.setLast("Ryan");
		assertEquals("Ryan", person.getLast());
		assertNotNull(person.getLast());
	}
	
	@Test
	public void testSetAndGetFirst() {
		Person person = new Person();
		assertNull(person.getFirst());
		person.setFirst("Alex");
		assertEquals("Alex", person.getFirst());
		assertNotNull(person.getFirst());
	}

	@Test
	public void testSetAndGetAge() {
		Person person = new Person();
		person.setAge(19);
		assertEquals(19, person.getAge());
		assertNotNull(person.getAge());
	}

}
