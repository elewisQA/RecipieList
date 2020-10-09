package com.qa.recipeLists.dto;
//---[ Imports ]---
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class RecipeDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(RecipeDTO.class).verify();
	}
}
