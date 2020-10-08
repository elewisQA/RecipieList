package com.qa.recipeLists.rest;

//---[ Imports ]---
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.recipeLists.dto.StepDTO;
import com.qa.recipeLists.persistence.domain.Step;
import com.qa.recipeLists.persistence.repository.StepRepo;

//---[ Testing Code ]---
@SpringBootTest
@AutoConfigureMockMvc
public class StepControllerIntegrationTest {

	//--[ Test Resources ]--
	@Autowired 
	private MockMvc mock;
	
	@Autowired
	private StepRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long id;
	private Step testStep;
	private Step testStepId;
	private StepDTO dto;
	
	//--[ Mapping Function ]--
	private StepDTO mapToDTO(Step Step) {
		return this.modelMapper.map(Step, StepDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testStep = new Step("Syrup", "Heat Sugar in a pan");
		this.testStepId = this.repo.save(testStep);
		this.id = testStepId.getId();
		this.dto = this.mapToDTO(this.testStepId);
	}
	
	@Test
	void createTest() throws Exception {
		this.mock
		// Mock a POST request
		.perform(request(HttpMethod.POST, "/step/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(testStep))
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isCreated())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
	}
	
	@Test
	void readTest() throws Exception {
		this.mock
		// Mock a GET request
		.perform(request(HttpMethod.GET, "/step/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isOk())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
	}
	
	@Test
	void readAllTest() throws Exception {
		List<Step> StepList = new ArrayList<>();
		this.mock
		// Mock a GET request
		.perform(request(HttpMethod.GET, "/step/read")
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	void updateTest() throws Exception {
		// Set-up fake data
		Step newStep = new Step("Step 1", "Combine Butter and Sugar");
		Step updatedStep = new Step(
				newStep.getName(),
				newStep.getDescription());
		updatedStep.setId(this.id);
		
		String result = this.mock
		// Mock a PUT request
		.perform(request(HttpMethod.PUT, "/step/update/" + this.id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newStep)))
		// Get returned values
		.andExpect(status().isAccepted())
		.andReturn().getResponse().getContentAsString();
		
		// Test assertion
		assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedStep)), 
				result);
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock
		// Mock a DELETE request
		.perform(request(HttpMethod.DELETE, "/step/delete/" + this.id))
		.andExpect(status().isNoContent());
	}
}
