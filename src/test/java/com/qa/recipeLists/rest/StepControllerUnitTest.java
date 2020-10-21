package com.qa.recipelists.rest;

//---[ Imports ]---
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.recipelists.dto.StepDTO;
import com.qa.recipelists.persistence.domain.Step;
import com.qa.recipelists.rest.StepController;
import com.qa.recipelists.service.StepService;

//---[ Testing Code ]---
@SpringBootTest
class StepControllerUnitTest {

	//--[ Test Resources ]--
	@Autowired
	private StepController controller;
	
	@MockBean
	private StepService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private List<Step> StepList;
	private Step testStep;
	private Step testStepId;
	private StepDTO dto;
	
	private final Long id = 1L;
	private final String exampleName = "Battenburg";
	private final String description = "Combine the butter and sugar";
	
	//--[ Mapping Function ]--
	private StepDTO mapToDTO(Step Step) {
		return this.modelMapper.map(Step, StepDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.StepList = new ArrayList<>();
		this.testStep = new Step(this.exampleName, this.description);
		this.testStepId = new Step(testStep.getName(), testStep.getDescription());
		this.testStepId.setId(this.id);
		this.StepList.add(testStepId);
		this.dto = this.mapToDTO(testStepId);
	}
	
	@Test
	void createTest() {
		// Set-up testing conditions
		when(this.service.create(testStep))
		.thenReturn(this.dto);
		
		// Test assertion
		StepDTO testCreated = this.dto;
		assertThat(new ResponseEntity<StepDTO>(testCreated, HttpStatus.CREATED))
		.isEqualTo(this.controller.create(testStep));
		
		// Check methods were called
		verify(this.service, times(1)).create(this.testStep);
	}
	
	@Test
	void readTest() {
		// Set-up testing conditions
		when(this.service.read(this.id))
		.thenReturn(this.dto);
		
		// Test assertion
		StepDTO testReadOne = this.dto;
		assertThat(new ResponseEntity<StepDTO>(testReadOne, HttpStatus.OK))
		.isEqualTo(this.controller.read(this.id));
		
		// Check methods were called
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void readAllTest() {
		// Set-up testing conditions
		when(this.service.read())
		.thenReturn(this.StepList.stream()
				.map(this::mapToDTO).collect(Collectors.toList()));
		
		// Test assertion
		assertThat(this.controller.read().getBody().isEmpty())
		.isFalse();
		
		// Check methods were called
		verify(this.service, times(1)).read();
	}
	
	@Test
	void updateTest() {
		// Set-up fake data
		Step newStep = new Step("Shortbread", "Combine Butter and Sugar");
		StepDTO newStepDTO = this.mapToDTO(newStep); // Quicker than making DTO lists
		StepDTO newStepDTOwithID = new StepDTO(
				this.id,
				newStepDTO.getName(),
				newStepDTO.getDescription());
		
		// Set-up testing conditions
		when(this.service.update(newStepDTO, this.id)).thenReturn(newStepDTOwithID);
		
		// Test assertion
		assertThat(new ResponseEntity<StepDTO>(newStepDTOwithID, HttpStatus.ACCEPTED))
		.isEqualTo(this.controller.update(this.id, newStepDTO));
		
		// Check methods were called
		verify(this.service, times(1)).update(newStepDTO, this.id);
	}
	
	@Test
	void deleteTest() {
		// Test condition
		this.controller.delete(this.id);
		
		// Check methods were called
		verify(this.service, times(1)).delete(this.id);
	}
}
