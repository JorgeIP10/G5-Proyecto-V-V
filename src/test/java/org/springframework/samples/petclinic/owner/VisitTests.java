package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VisitTests {

	private Visit visit;

	@BeforeEach
	void setUp() {
		visit = new Visit();
	}

	@Test
	void testDefaultConstructorSetsDateToToday() {
		LocalDate today = LocalDate.now();
		assertEquals(today, visit.getDate());
	}

	@Test
	void testSetDate() {
		LocalDate date = LocalDate.of(2023, 12, 1);
		visit.setDate(date);

		assertEquals(date, visit.getDate());
	}

	@Test
	void testSetDescription() {
		String description = "Annual check-up";
		visit.setDescription(description);

		assertEquals(description, visit.getDescription());
	}

	@Test
	void testDescriptionValidation() {
		assertThrows(IllegalArgumentException.class, () -> visit.setDescription(null));
	}

}
