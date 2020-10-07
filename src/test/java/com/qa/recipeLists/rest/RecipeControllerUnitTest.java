package com.qa.recipeLists.rest;

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

import com.qa.recipeLists.dto.RecipeDTO;
import com.qa.recipeLists.persistence.domain.Recipe;
import com.qa.recipeLists.service.RecipeService;

//---[ Testing Code ]---
@SpringBootTest
public class RecipeControllerUnitTest {

	//--[ Test Resources ]--
	@Autowired
	private RecipeController controller;
	
	@MockBean
	private RecipeService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private List<Recipe> recipeList;
	private Recipe testRecipe;
	private Recipe testRecipeId;
	private RecipeDTO dto;
	
	private final Long id = 1L;
	private final String exampleName = "Battenburg";
	
	//--[ Mapping Function ]--
	private RecipeDTO mapToDTO(Recipe recipe) {
		return this.modelMapper.map(recipe, RecipeDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.recipeList = new ArrayList<>();
		this.testRecipe = new Recipe(this.exampleName);
		this.testRecipeId = new Recipe(testRecipe.getName());
		this.testRecipeId.setId(this.id);
		this.recipeList.add(testRecipeId);
		this.dto = this.mapToDTO(testRecipeId);
	}
	
	@Test
	void createTest() {
		// Set-up testing conditions
		when(this.service.create(testRecipe))
		.thenReturn(this.dto);
		
		// Test assertion
		RecipeDTO testCreated = this.dto;
		assertThat(new ResponseEntity<RecipeDTO>(testCreated, HttpStatus.CREATED))
		.isEqualTo(this.controller.create(testRecipe));
		
		// Check methods were called
		verify(this.service, times(1)).create(this.testRecipe);
	}
	
	@Test
	void readTest() {
		// Set-up testing conditions
		when(this.service.read(this.id))
		.thenReturn(this.dto);
		
		// Test assertion
		RecipeDTO testReadOne = this.dto;
		assertThat(new ResponseEntity<RecipeDTO>(testReadOne, HttpStatus.OK))
		.isEqualTo(this.controller.read(this.id));
		
		// Check methods were called
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void readAllTest() {
		// Set-up testing conditions
		when(this.service.read())
		.thenReturn(this.recipeList.stream()
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
		Recipe newRecipe = new Recipe("Shortbread");
		RecipeDTO newRecipeDTO = this.mapToDTO(newRecipe); // Quicker than making DTO lists
		RecipeDTO newRecipeDTOwithID = new RecipeDTO(
				this.id,
				newRecipeDTO.getName(),
				newRecipeDTO.getIngredients(),
				newRecipeDTO.getSteps());
		
		// Set-up testing conditions
		when(this.service.update(newRecipeDTO, this.id)).thenReturn(newRecipeDTOwithID);
		
		// Test assertion
		assertThat(new ResponseEntity<RecipeDTO>(newRecipeDTOwithID, HttpStatus.ACCEPTED))
		.isEqualTo(this.controller.update(this.id, newRecipeDTO));
		
		// Check methods were called
		verify(this.service, times(1)).update(newRecipeDTO, this.id);
	}
	
	@Test
	void deleteTest() {
		// Test condition
		this.controller.delete(this.id);
		
		// Check methods were called
		verify(this.service, times(1)).delete(this.id);
	}
}
