package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setAddress("123 Main Street");
		owner.setCity("Springfield");
		owner.setTelephone("1234567890");
	}

	@Test
	void testAddPet() {
		Pet pet = new Pet();
		pet.setName("Buddy");

		owner.addPet(pet);

		assertEquals(1, owner.getPets().size());
		assertTrue(owner.getPets().contains(pet));
	}

	@Test
	void testGetPetByName() {
		Pet pet = new Pet();
		pet.setName("Buddy");

		owner.getPets().add(pet); // Agregar directamente a la lista

		assertNotNull(owner.getPet("Buddy"));
		assertEquals("Buddy", owner.getPet("Buddy").getName());
	}

	@Test
	void testGetPetById() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		pet.setId(1);

		owner.getPets().add(pet); // Agregar directamente a la lista

		assertNotNull(owner.getPet(1));
		assertEquals(1, owner.getPet(1).getId());
	}

	@Test
	void testGetPetIgnoreNew() {
		Pet pet = new Pet();
		pet.setName("Buddy");

		owner.addPet(pet);

		assertNull(owner.getPet("Buddy", true));
	}

	@Test
	void testAddVisitToPet() {
		Pet pet = new Pet();
		pet.setId(1);
		owner.getPets().add(pet); // Agregar directamente a la lista

		Visit visit = new Visit();
		visit.setDescription("Check-up");

		owner.addVisit(1, visit);

		assertEquals(1, pet.getVisits().size());
		assertTrue(pet.getVisits().contains(visit));
	}

	@Test
	void testToString() {
		String result = owner.toString();
		assertTrue(result.contains("123 Main Street"));
		assertTrue(result.contains("Springfield"));
		assertTrue(result.contains("1234567890"));
	}

}
