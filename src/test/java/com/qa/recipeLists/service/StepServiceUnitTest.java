package com.qa.recipeLists.service;

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

import com.qa.recipeLists.dto.StepDTO;
import com.qa.recipeLists.persistence.domain.Step;
import com.qa.recipeLists.persistence.repository.StepRepo;

//---[ Testing Code ]---
@SpringBootTest
public class StepServiceUnitTest {

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
}
