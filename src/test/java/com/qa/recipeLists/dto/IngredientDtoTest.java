package com.qa.recipeLists.dto;
//---[ Imports ]---
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

//---[ Testing Code ]---
public class IngredientDtoTest {
	//--[ Test Cases ]--
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(IngredientDTO.class).verify();
	}
}
