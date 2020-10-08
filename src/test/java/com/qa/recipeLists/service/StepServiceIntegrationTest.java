package com.qa.recipeLists.service;

//---[ Imports ]---
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.recipeLists.dto.StepDTO;
import com.qa.recipeLists.dto.RecipeDTO;
import com.qa.recipeLists.persistence.domain.Step;
import com.qa.recipeLists.persistence.domain.Recipe;
import com.qa.recipeLists.persistence.repository.StepRepo;

//---[ Testing Code ]---
@SpringBootTest
public class StepServiceIntegrationTest {

	//--[ Test Resources ]--
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StepService service;
	
	@Autowired
	private StepRepo repo;
	
	private Step testStep;
	private Step testStepId;
	private StepDTO dto;
	
	private Long id;
	private final String exampleName = "Step 1";
	private final String exampleDescription = "Eat the eggs";
	
	//--[ Mapping Function ]--
	private StepDTO mapToDTO(Step Step) {
		return this.modelMapper.map(Step, StepDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Clear Data to ensure clean test
		this.testStep = new Step(
				this.exampleName, 
				this.exampleDescription);
		this.testStepId = this.repo.save(testStep); // Saving returns with id
		this.id = testStepId.getId();
		this.dto = this.mapToDTO(testStepId);
	}
	
	@Test
	void testCreate() {
		assertThat(this.dto)
		.isEqualTo(this.service.create(testStep));
	}
	
	@Test
	void testRead() {
		assertThat(this.dto)
		.isEqualTo(this.service.read(this.id));
	}
	
	@Test
	void testReadAll() {
		assertThat(this.service.read())	// Read-All method returns list. 
			.isEqualTo(Stream.of(			// Test data is streamed to a list
				this.dto)
				.collect(Collectors.toList()));
	}
	
	@Test
	void testUpdate() {
		// Set-up test data
		
		StepDTO newStep = new StepDTO(
				null, 
				"Step 2", 
				"Eat the butter");
		StepDTO updatedStep = new StepDTO(
				this.id, 
				newStep.getName(), 
				newStep.getDescription());
		
		// Test assertion
		assertThat(updatedStep)
		.isEqualTo(this.service.update(newStep, this.id));
	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.testStep.getId()))
		.isTrue();
	}
}
