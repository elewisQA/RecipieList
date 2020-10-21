package com.qa.recipelists.dto;

//---[ Imports ]---
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class StepDtoTest {

	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(StepDTO.class).verify();
	}
}
