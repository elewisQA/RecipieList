package com.qa.recipelists.dto;
//---[ Imports ]---
import org.junit.jupiter.api.Test;

import com.qa.recipelists.dto.IngredientDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

//---[ Testing Code ]---
public class IngredientDtoTest {
	//--[ Test Cases ]--
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(IngredientDTO.class).verify();
	}
}
