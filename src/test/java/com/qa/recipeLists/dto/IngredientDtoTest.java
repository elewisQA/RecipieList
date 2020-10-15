package com.qa.recipelists.dto;

//---[ Imports ]---
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

//---[ Testing Code ]---
class IngredientDtoTest {
	//--[ Test Cases ]--
	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(IngredientDTO.class).verify();
	}
}
