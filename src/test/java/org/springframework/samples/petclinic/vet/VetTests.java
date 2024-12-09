/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class VetTests {

	private Vet vet;

	@BeforeEach
	void setUp() {
		vet = new Vet();
	}

	@Test
	void testSerialization() {
		Vet vet = new Vet();
		vet.setFirstName("Zaphod");
		vet.setLastName("Beeblebrox");
		vet.setId(123);
		@SuppressWarnings("deprecation")
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());
	}

	@Test
	void testAddSpecialty() {
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");

		vet.addSpecialty(specialty);

		assertEquals(1, vet.getNrOfSpecialties());
		assertTrue(vet.getSpecialties().contains(specialty));
	}

	@Test
	void testGetNrOfSpecialties() {
		assertEquals(0, vet.getNrOfSpecialties());

		Specialty specialty1 = new Specialty();
		specialty1.setName("Radiology");
		vet.addSpecialty(specialty1);

		Specialty specialty2 = new Specialty();
		specialty2.setName("Dentistry");
		vet.addSpecialty(specialty2);

		assertEquals(2, vet.getNrOfSpecialties());
	}

	@Test
	void testGetSpecialties() {
		Specialty specialty1 = new Specialty();
		specialty1.setName("Radiology");
		vet.addSpecialty(specialty1);

		Specialty specialty2 = new Specialty();
		specialty2.setName("Dentistry");
		vet.addSpecialty(specialty2);

		List<Specialty> specialties = vet.getSpecialties();
		assertEquals(2, specialties.size());
		assertEquals("Dentistry", specialties.get(0).getName());
		assertEquals("Radiology", specialties.get(1).getName());
	}

	@Test
	void testGetSpecialtiesUnmodifiable() {
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");

		vet.addSpecialty(specialty);

		List<Specialty> specialties = vet.getSpecialties();

		assertThrows(UnsupportedOperationException.class, () -> specialties.add(new Specialty()));
	}

}
