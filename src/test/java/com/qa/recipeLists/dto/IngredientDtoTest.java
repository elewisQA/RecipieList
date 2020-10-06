package com.qa.recipeLists.dto;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class IngredientDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(IngredientDTO.class).verify();
	}
}
