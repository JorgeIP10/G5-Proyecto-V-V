package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PetTests {

	@Test
	void testSetAndGetBirthDate() {
		Pet pet = new Pet();
		LocalDate birthDate = LocalDate.of(2020, 1, 1);

		pet.setBirthDate(birthDate);

		assertEquals(birthDate, pet.getBirthDate());
	}

	@Test
	void testSetAndGetType() {
		Pet pet = new Pet();
		PetType type = new PetType();
		type.setName("Dog");

		pet.setType(type);

		assertEquals("Dog", pet.getType().getName());
	}

	@Test
	void testAddVisit() {
		Pet pet = new Pet();
		Visit visit = new Visit();
		visit.setDescription("Vaccination");

		pet.addVisit(visit);

		assertEquals(1, pet.getVisits().size());
		assertTrue(pet.getVisits().contains(visit));
	}

}
