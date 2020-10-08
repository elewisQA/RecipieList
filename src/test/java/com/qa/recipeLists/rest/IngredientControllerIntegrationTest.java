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
import com.qa.recipeLists.dto.IngredientDTO;
import com.qa.recipeLists.persistence.domain.Ingredient;
import com.qa.recipeLists.persistence.repository.IngredientRepo;

//---[ Testing Code ]---
@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerIntegrationTest {

	//--[ Test Resources ]--
	@Autowired 
	private MockMvc mock;
	
	@Autowired
	private IngredientRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long id;
	private Ingredient testIngredient;
	private Ingredient testIngredientId;
	private IngredientDTO dto;
	
	//--[ Mapping Function ]--
	private IngredientDTO mapToDTO(Ingredient ingredient) {
		return this.modelMapper.map(ingredient, IngredientDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testIngredient = new Ingredient("Flour", "g", (float) 175.0);
		this.testIngredientId = this.repo.save(testIngredient);
		this.id = testIngredientId.getId();
		this.dto = this.mapToDTO(this.testIngredientId);
	}
	
	@Test
	void createTest() throws Exception {
		this.mock
		// Mock a POST request
		.perform(request(HttpMethod.POST, "/ingredient/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(testIngredient))
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isCreated())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
	}
	
	@Test
	void readTest() throws Exception {
		this.mock
		// Mock a GET request
		.perform(request(HttpMethod.GET, "/ingredient/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isOk())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.dto)));
	}
	
	@Test
	void readAllTest() throws Exception {
		List<Ingredient> IngredientList = new ArrayList<>();
		this.mock
		// Mock a GET request
		.perform(request(HttpMethod.GET, "/ingredient/read")
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	void updateTest() throws Exception {
		// Set-up fake data
		Ingredient newIngredient = new Ingredient(
				"Butter", 
				"oz", 
				(float) 8);
		Ingredient updatedIngredient = new Ingredient(
				newIngredient.getName(),
				newIngredient.getUnit(),
				newIngredient.getQuantity());
		updatedIngredient.setId(this.id);
		
		String result = this.mock
		// Mock a PUT request
		.perform(request(HttpMethod.PUT, "/ingredient/update/" + this.id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newIngredient)))
		// Get returned values
		.andExpect(status().isAccepted())
		.andReturn().getResponse().getContentAsString();
		
		// Test assertion
		assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedIngredient)), 
				result);
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock
		// Mock a DELETE request
		.perform(request(HttpMethod.DELETE, "/ingredient/delete/" + this.id))
		.andExpect(status().isNoContent());
	}
}
