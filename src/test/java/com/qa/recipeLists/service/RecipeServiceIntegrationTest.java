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

import com.qa.recipeLists.dto.IngredientDTO;
import com.qa.recipeLists.dto.RecipeDTO;
import com.qa.recipeLists.dto.StepDTO;
import com.qa.recipeLists.persistence.domain.Recipe;
import com.qa.recipeLists.persistence.repository.RecipeRepo;
import com.qa.recipeLists.utils.RecipeListsUtils;

//---[ Testing Code ]---
@SpringBootTest
public class RecipeServiceIntegrationTest {
	
	//--[ Test Resources ]--
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RecipeService service;
	
	@Autowired
	private RecipeRepo repo;
	
	private Recipe testRecipe;
	private Recipe testRecipeId;
	private RecipeDTO dto;
	private List<IngredientDTO> ingredients;
	private List<StepDTO> steps;
	
	private Long id;
	private final String exampleName = "Battenburg";
	
	//--[ Mapping Function ]--
	private RecipeDTO mapToDTO(Recipe recipe) {
		System.out.println("Recipe to Map:\n" + recipe.toString());
		return this.modelMapper.map(recipe, RecipeDTO.class);
	}

	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Clear Data to ensure clean test
		this.testRecipe = new Recipe(this.exampleName);
		this.testRecipeId = this.repo.save(testRecipe); // Saving returns with id
		this.id = testRecipeId.getId();
		//this.dto = this.mapToDTO(testRecipeId);
		this.dto = RecipeListsUtils.mapRecipeToDTO(testRecipeId);
	}
	
	@Test
	void testCreate() {
		assertThat(this.dto)
		.isEqualTo(this.service.create(testRecipe));
	}
	
	/*
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
		
		RecipeDTO newRecipe = new RecipeDTO(
				null, 
				"Shortbread", 
				ingredients, 
				steps);
		RecipeDTO updatedRecipe = new RecipeDTO(
				this.id, 
				newRecipe.getName(),
				newRecipe.getIngredients(), 
				newRecipe.getSteps());
		
		// Test assertion
		assertThat(updatedRecipe)
		.isEqualTo(this.service.update(newRecipe, this.id));
	}
	*/
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.testRecipe.getId()))
		.isTrue();
	}
}
