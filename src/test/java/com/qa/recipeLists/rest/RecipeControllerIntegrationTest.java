package com.qa.recipeLists.rest;

//---[ Imports ]---
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.recipeLists.dto.RecipeDTO;
import com.qa.recipeLists.persistence.domain.Recipe;
import com.qa.recipeLists.persistence.repository.RecipeRepo;
import com.qa.recipeLists.service.RecipeService;

//---[ Testing Code ]---
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

	//--[ Test Resources ]--
	@Autowired 
	private MockMvc mock;
	
	@Autowired
	private RecipeRepo repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long id;
	private Recipe testRecipe;
	private Recipe testRecipeId;
	
	//--[ Mapping Function ]--
	private RecipeDTO mapToDTO(Recipe recipe) {
		return this.modelMapper.map(recipe, RecipeDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testRecipe = new Recipe("Battenburg");
		this.testRecipeId = this.repo.save(testRecipe);
		this.id = testRecipeId.getId();
	}
	
	@Test
	void createTest() throws Exception {
		this.mock
		// Mock a POST request
		.perform(request(HttpMethod.POST, "/recipe/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(testRecipe))
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isCreated())
		.andExpect(content().json(this.objectMapper.writeValueAsString(testRecipeId)));
	}
	
	@Test
	void readTest() throws Exception {
		this.mock
		// Mock a GET request
		.perform(request(HttpMethod.GET, "/recipe/read/" + this.id)
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isOk())
		.andExpect(content().json(this.objectMapper.writeValueAsString(this.testRecipe)));
	}
	
	@Test
	void readAllTest() throws Exception {
		List<Recipe> recipeList = new ArrayList<>();
		this.mock
		// Mock a GET request
		.perform(request(HttpMethod.GET, "/recipe/read")
				.accept(MediaType.APPLICATION_JSON))
		// Test return values against expected
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	void updateTest() throws Exception {
		// Set-up fake data
		Recipe newRecipe = new Recipe("Shortbread");
		Recipe updatedRecipe = new Recipe(newRecipe.getName());
		updatedRecipe.setId(this.id);
		
		String result = this.mock
		// Mock a PUT request
		.perform(request(HttpMethod.PUT, "/recipe/update/" + this.id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(newRecipe)))
		// Get returned values
		.andExpect(status().isAccepted())
		.andReturn().getResponse().getContentAsString();
		
		// Test assertion
		assertEquals(this.objectMapper.writeValueAsString(this.mapToDTO(updatedRecipe)), 
				result);
	}
	
	@Test
	void deleteTest() throws Exception {
		this.mock
		// Mock a DELETE request
		.perform(request(HttpMethod.DELETE, "/recipe/delete/" + this.id))
		.andExpect(status().isNoContent());
	}
}
