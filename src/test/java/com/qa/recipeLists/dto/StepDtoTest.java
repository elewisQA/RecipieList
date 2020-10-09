package com.qa.recipeLists.dto;
//---[ Imports ]---
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class StepDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(StepDTO.class).verify();
	}
}
