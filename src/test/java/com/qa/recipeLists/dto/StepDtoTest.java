package com.qa.recipelists.dto;
//---[ Imports ]---
import org.junit.jupiter.api.Test;

import com.qa.recipelists.dto.StepDTO;

import nl.jqno.equalsverifier.EqualsVerifier;

public class StepDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(StepDTO.class).verify();
	}
}
