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

import com.qa.recipeLists.dto.IngredientDTO;
import com.qa.recipeLists.persistence.domain.Ingredient;
import com.qa.recipeLists.service.IngredientService;

//---[ Testing Code ]---
@SpringBootTest
public class IngredientControllerUnitTest {

	//--[ Test Resources ]--
	@Autowired
	private IngredientController controller;
	
	@MockBean
	private IngredientService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private List<Ingredient> IngredientList;
	private Ingredient testIngredient;
	private Ingredient testIngredientId;
	private IngredientDTO dto;
	
	private final Long id = 1L;
	private final String exampleName = "Battenburg";
	private final String unit = "g";
	private final Float quantity = (float) 175.0;
	
	//--[ Mapping Function ]--
	private IngredientDTO mapToDTO(Ingredient Ingredient) {
		return this.modelMapper.map(Ingredient, IngredientDTO.class);
	}
	
	//---[ Test Cases ]---
	@BeforeEach
	void init() {
		this.IngredientList = new ArrayList<>();
		this.testIngredient = new Ingredient(this.exampleName, this.unit, this.quantity);
		this.testIngredientId = new Ingredient(
				testIngredient.getName(),
				testIngredient.getUnit(),
				testIngredient.getQuantity());
		this.testIngredientId.setId(this.id);
		this.IngredientList.add(testIngredientId);
		this.dto = this.mapToDTO(testIngredientId);
	}
	
	@Test
	void createTest() {
		// Set-up testing conditions
		when(this.service.create(testIngredient))
		.thenReturn(this.dto);
		
		// Test assertion
		IngredientDTO testCreated = this.dto;
		assertThat(new ResponseEntity<IngredientDTO>(testCreated, HttpStatus.CREATED))
		.isEqualTo(this.controller.create(testIngredient));
		
		// Check methods were called
		verify(this.service, times(1)).create(this.testIngredient);
	}
	
	@Test
	void readTest() {
		// Set-up testing conditions
		when(this.service.read(this.id))
		.thenReturn(this.dto);
		
		// Test assertion
		IngredientDTO testReadOne = this.dto;
		assertThat(new ResponseEntity<IngredientDTO>(testReadOne, HttpStatus.OK))
		.isEqualTo(this.controller.read(this.id));
		
		// Check methods were called
		verify(this.service, times(1)).read(this.id);
	}
	
	@Test
	void readAllTest() {
		// Set-up testing conditions
		when(this.service.read())
		.thenReturn(this.IngredientList.stream()
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
		Ingredient newIngredient = new Ingredient("Shortbread", "oz", (float) 50.0);
		IngredientDTO newIngredientDTO = this.mapToDTO(newIngredient); // Quicker than making DTO lists
		IngredientDTO newIngredientDTOwithID = new IngredientDTO(
				this.id,
				newIngredientDTO.getName(),
				newIngredientDTO.getUnit(),
				newIngredientDTO.getQuantity());
		
		// Set-up testing conditions
		when(this.service.update(newIngredientDTO, this.id)).thenReturn(newIngredientDTOwithID);
		
		// Test assertion
		assertThat(new ResponseEntity<IngredientDTO>(newIngredientDTOwithID, HttpStatus.ACCEPTED))
		.isEqualTo(this.controller.update(this.id, newIngredientDTO));
		
		// Check methods were called
		verify(this.service, times(1)).update(newIngredientDTO, this.id);
	}
	
	@Test
	void deleteTest() {
		// Test condition
		this.controller.delete(this.id);
		
		// Check methods were called
		verify(this.service, times(1)).delete(this.id);
	}
}
