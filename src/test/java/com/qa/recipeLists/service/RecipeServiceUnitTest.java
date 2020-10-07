package com.qa.recipeLists.service;

//---[ Imports ]---
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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

//---[ Testing Code ]---
@SpringBootTest
public class RecipeServiceUnitTest {

	//--[ Test Resources ]--
	@Autowired
	private RecipeService service;
	
	@MockBean
	private RecipeRepo repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private List<Recipe> recipeList;
	private Recipe testRecipe;
	private Recipe testRecipeId;
	private Recipe emptyRecipe;
	private RecipeDTO dto;
	
	final Long id = 1L;
	final String testName = "Victoria Sponge";
	
	//--[ Mapping Function ]--
	private RecipeDTO mapToDTO(Recipe recipe) {
		return this.modelMapper.map(recipe, RecipeDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		// Instantiate lists to emulate returned data
		this.recipeList = new ArrayList<>();
		this.recipeList.add(testRecipe);
		
		// Create Example Recipe 
		this.testRecipe = new Recipe(testName);
		
		// Create Example Recipe with an Id
		this.testRecipeId = new Recipe(testRecipe.getName());
		this.testRecipeId.setId(id);
		
		// Create Example empty Recipe
		this.emptyRecipe = new Recipe();
		
		// Create Recipe DTO using Recipe with Id
		this.dto = new ModelMapper().map(testRecipeId, RecipeDTO.class);
	}
	
	@Test
	void createTest() {
		// Set-up testing conditions
		when(this.modelMapper.map(mapToDTO(testRecipe), Recipe.class))
		.thenReturn(testRecipe);
		
		when(this.repo.save(testRecipe))
		.thenReturn(testRecipeId);
		
		when(this.modelMapper.map(testRecipeId, RecipeDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.dto).isEqualTo(this.service.create(testRecipe));
		
		// Check methods were called
		verify(this.repo, times(1)).save(this.testRecipe);
	}
	
	@Test
	void readTest() {
		// Set-up testing conditions
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(this.testRecipeId));
		
		when(this.modelMapper.map(testRecipeId, RecipeDTO.class))
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
		.thenReturn(this.recipeList);
		
		when(this.modelMapper.map(testRecipeId, RecipeDTO.class))
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
		List<IngredientDTO> ingredients = new ArrayList<>();
		List<StepDTO> steps = new ArrayList<>();
		RecipeDTO newRecipe = new RecipeDTO(null, "Battenburg", ingredients, steps);
		
		Recipe recipe = new Recipe("Battenburg");
		recipe.setId(ID);
		
		Recipe updatedRecipe = new Recipe(newRecipe.getName());
		updatedRecipe.setId(ID);
		
		RecipeDTO updatedDTO = mapToDTO(updatedRecipe);
		
		// Set-up testing conditions
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(recipe));
		
		when(this.repo.save(updatedRecipe))
		.thenReturn(updatedRecipe);
		
		when(this.modelMapper.map(updatedRecipe, RecipeDTO.class))
		.thenReturn(updatedDTO);
		
		// Test assertion
		assertThat(updatedDTO).isEqualTo(this.service.update(newRecipe, this.id));
		
		// Check methods were called
		verify(this.repo, times(1)).findById(ID);
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
