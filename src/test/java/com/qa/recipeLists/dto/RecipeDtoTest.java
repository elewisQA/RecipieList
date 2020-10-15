package com.qa.recipelists.dto;
//---[ Imports ]---
import org.junit.jupiter.api.Test;

import com.qa.recipelists.dto.RecipeDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

public class RecipeDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(RecipeDTO.class).verify();
	}
}
