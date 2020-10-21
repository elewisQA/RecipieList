package com.qa.recipelists.dto;

//---[ Imports ]---
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

class RecipeDtoTest {

	@Test
	void testEquals() {
		EqualsVerifier.simple().forClass(RecipeDTO.class).verify();
	}
}
