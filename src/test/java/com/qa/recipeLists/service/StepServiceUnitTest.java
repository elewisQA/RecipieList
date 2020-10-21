package com.qa.recipelists.service;

//---[ Imports ]---
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.recipelists.dto.StepDTO;
import com.qa.recipelists.persistence.domain.Step;
import com.qa.recipelists.persistence.repository.StepRepo;
import com.qa.recipelists.service.StepService;

//---[ Testing Code ]---
@SpringBootTest
class StepServiceUnitTest {

	//--[ Test Resources ]--
	@Autowired
	private StepService service;
	
	@MockBean
	private StepRepo repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private List<Step> stepList;
	private Step testStep;
	private Step testStepId;
	private Step emptyStep;
	private StepDTO dto;
	
	final Long id = 1L;
	final String testName = "Step 1";
	final String testDescription = "Combine butter and sugar";
	
	//--[ Mapping Function ]--
	private StepDTO mapToDTO(Step step) {
		return this.modelMapper.map(step, StepDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		// Instantiate list to emulate returned data
		this.stepList = new ArrayList<>();
		this.stepList.add(testStep);
		
		// Create Example Step
		this.testStep = new Step();
		testStep.setName(testName);
		testStep.setDescription(testDescription);
		
		// Create Example Step with an Id
		this.testStepId = new Step();
		testStepId.setName(testStep.getName());
		testStepId.setDescription(testStep.getDescription());
		testStepId.setId(this.id);
		
		// Create Example empty step
		this.emptyStep = new Step();
		
		// Create Step DTO using Step with Id
		this.dto = new ModelMapper().map(testStepId, StepDTO.class);
	}
	
	@Test
	void createTest() {
		// Set-up testing conditions
		when(this.modelMapper.map(mapToDTO(testStep), Step.class))
		.thenReturn(testStep);
		
		when(this.repo.save(testStep))
		.thenReturn(testStepId);
		
		when(this.modelMapper.map(testStepId, StepDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.dto).isEqualTo(this.service.create(testStep));
		
	}
	
	@Test
	void readTest() {
		// Set-up testing conditions
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(this.testStepId));
		
		when(this.modelMapper.map(testStepId, StepDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.dto).isEqualTo(this.service.read(this.id));
		
		// Check methods were called
		verify(this.repo, times(1)).findById(this.id);
	}
	
	@Test
	void readAllTest() {
		// Set-up testing conditions
		when(repo.findAll())
		.thenReturn(this.stepList);
		
		when(this.modelMapper.map(testStepId, StepDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.service.read().isEmpty()).isFalse();
		
		// Check methods were called
		verify(repo, times(1)).findAll();
	}
	
	@Test
	void updateTest() {
		// Set-up fake data
		final Long ID = 1L;
		StepDTO newStep = new StepDTO(null, "Step 2", "Add in flour");
		
		Step step = new Step("Step 2", "Add in flour");
		step.setId(ID);;
		
		Step updatedStep = new Step(newStep.getName(), newStep.getDescription());
		updatedStep.setId(ID);
		
		StepDTO updatedDTO = new StepDTO(ID, updatedStep.getName(), updatedStep.getDescription());
	
		// Set-up testing conditions
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(step));
		
		when(this.repo.save(updatedStep))
		.thenReturn(updatedStep);
		
		when(this.modelMapper.map(updatedStep, StepDTO.class))
		.thenReturn(updatedDTO);
		
		// Test assertion
		assertThat(updatedDTO).isEqualTo(this.service.update(newStep, this.id));
		
		// Check methods were called
		verify(this.repo, times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedStep);
	}
	
	@Test
	void deleteTest() {
		// Set-up testing conditions
		when(this.repo.existsById(this.id))
		.thenReturn(true, false);
				
		// Test assertions
		assertThat(this.service.delete(id)).isTrue();
				
		// Check methods were called
		verify(this.repo, times(1)).deleteById(this.id);
		verify(this.repo, times(2)).existsById(this.id);
	}
}
