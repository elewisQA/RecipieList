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

import com.qa.recipelists.dto.IngredientDTO;
import com.qa.recipelists.persistence.domain.Ingredient;
import com.qa.recipelists.persistence.repository.IngredientRepo;
import com.qa.recipelists.service.IngredientService;

//---[ Testing Code ]---
@SpringBootTest
class IngredientServiceUnitTest {

	//--[ Test Resources ]--
	@Autowired
	private IngredientService service;
	
	@MockBean
	private IngredientRepo repo;
	
	@MockBean
	private ModelMapper modelMapper;
	
	private List<Ingredient> IngredientList;
	private Ingredient testIngredient;
	private Ingredient testIngredientId;
	private Ingredient emptyIngredient;
	private IngredientDTO dto;
	
	final Long id = 1L;
	final String testName = "Ingredient 1";
	final String testUnit = "Combine butter and sugar";
	final Float testQuantity = (float) 175.0;
	
	//--[ Mapping Function ]--
	private IngredientDTO mapToDTO(Ingredient Ingredient) {
		return this.modelMapper.map(Ingredient, IngredientDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		// Instantiate list to emulate returned data
		this.IngredientList = new ArrayList<>();
		this.IngredientList.add(testIngredient);
		
		// Create Example Ingredient
		this.testIngredient = new Ingredient();
		testIngredient.setName(testName);
		testIngredient.setQuantity(testQuantity);
		testIngredient.setUnit(testUnit);
		
		// Create Example Ingredient with an Id
		this.testIngredientId = new Ingredient();
		testIngredientId.setName(testIngredient.getName());
		testIngredientId.setQuantity(testIngredient.getQuantity());
		testIngredientId.setUnit(testIngredient.getUnit());
		testIngredientId.setId(this.id);
		
		// Create Example empty Ingredient
		this.emptyIngredient = new Ingredient();
		
		// Create Ingredient DTO using Ingredient with Id
		this.dto = new ModelMapper().map(testIngredientId, IngredientDTO.class);
	}
	
	@Test
	void createTest() {
		// Set-up testing conditions
		when(this.modelMapper.map(mapToDTO(testIngredient), Ingredient.class))
		.thenReturn(testIngredient);
		
		when(this.repo.save(testIngredient))
		.thenReturn(testIngredientId);
		
		when(this.modelMapper.map(testIngredientId, IngredientDTO.class))
		.thenReturn(dto);
		
		// Test assertion
		assertThat(this.dto).isEqualTo(this.service.create(testIngredient));
		
	}
	
	@Test
	void readTest() {
		// Set-up testing conditions
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(this.testIngredientId));
		
		when(this.modelMapper.map(testIngredientId, IngredientDTO.class))
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
		.thenReturn(this.IngredientList);
		
		when(this.modelMapper.map(testIngredientId, IngredientDTO.class))
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
		IngredientDTO newIngredient = new IngredientDTO(
				null, 
				"Flour", 
				"g", 
				(float) 175.0);
		Ingredient ingredient = new Ingredient(
				"Flour", 
				"g", 
				(float) 175.0);
		ingredient.setId(ID);
		
		Ingredient updatedIngredient = new Ingredient(
				newIngredient.getName(), 
				newIngredient.getUnit(),
				newIngredient.getQuantity());
		updatedIngredient.setId(ID);
		
		IngredientDTO updatedDTO = new IngredientDTO(
				ID, 
				updatedIngredient.getName(), 
				updatedIngredient.getUnit(),
				updatedIngredient.getQuantity());
		// Set-up testing conditions
		when(this.repo.findById(this.id))
		.thenReturn(Optional.of(ingredient));
		
		when(this.repo.save(updatedIngredient))
		.thenReturn(updatedIngredient);
		
		when(this.modelMapper.map(updatedIngredient, IngredientDTO.class))
		.thenReturn(updatedDTO);
		
		// Test assertion
		assertThat(updatedDTO).isEqualTo(this.service.update(newIngredient, this.id));
		
		// Check methods were called
		verify(this.repo, times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedIngredient);
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
