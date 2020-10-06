package com.qa.recipeLists.dto;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class RecipeDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(RecipeDTO.class).verify();
	}
}
