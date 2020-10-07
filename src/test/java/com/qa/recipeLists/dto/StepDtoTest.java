package com.qa.recipeLists.dto;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class StepDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(StepDTO.class).verify();
	}
}
