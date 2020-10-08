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
import com.qa.recipeLists.persistence.domain.Ingredient;
import com.qa.recipeLists.persistence.domain.Recipe;
import com.qa.recipeLists.persistence.repository.IngredientRepo;

//---[ Testing Code ]---
@SpringBootTest
public class IngredientServiceIntegrationTest {

	//--[ Test Resources ]--
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IngredientService service;
	
	@Autowired
	private IngredientRepo repo;
	
	private Ingredient testIngredient;
	private Ingredient testIngredientId;
	private IngredientDTO dto;
	
	private Long id;
	private final String exampleName = "Flour";
	private final String exampleUnit = "g";
	private final float exampleQty = (float) 75.0;
	
	//--[ Mapping Function ]--
	private IngredientDTO mapToDTO(Ingredient ingredient) {
		return this.modelMapper.map(ingredient, IngredientDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.repo.deleteAll(); // Clear Data to ensure clean test
		this.testIngredient = new Ingredient(
				this.exampleName, 
				this.exampleUnit, 
				this.exampleQty);
		this.testIngredientId = this.repo.save(testIngredient); // Saving returns with id
		this.id = testIngredientId.getId();
		this.dto = this.mapToDTO(testIngredientId);
	}
	
	@Test
	void testCreate() {
		assertThat(this.dto)
		.isEqualTo(this.service.create(testIngredient));
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
		
		IngredientDTO newIngredient = new IngredientDTO(
				null, 
				"Shortbread", 
				"g",
				(float) 50.0);
		IngredientDTO updatedIngredient = new IngredientDTO(
				this.id, 
				newIngredient.getName(),
				newIngredient.getUnit(), 
				newIngredient.getQuantity());
		
		// Test assertion
		assertThat(updatedIngredient)
		.isEqualTo(this.service.update(newIngredient, this.id));
	}
	
	@Test
	void testDelete() {
		assertThat(this.service.delete(this.testIngredient.getId()))
		.isTrue();
	}
}
