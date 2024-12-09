package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTests {

	private Person person;

	@BeforeEach
	void setUp() {
		person = new Person() {
		};
	}

	@Test
	void testSetFirstName() {
		String firstName = "John";
		person.setFirstName(firstName);

		assertEquals(firstName, person.getFirstName());
	}

	@Test
	void testSetLastName() {
		String lastName = "Doe";
		person.setLastName(lastName);

		assertEquals(lastName, person.getLastName());
	}

}
