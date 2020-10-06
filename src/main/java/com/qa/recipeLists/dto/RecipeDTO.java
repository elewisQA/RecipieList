package com.qa.recipeLists.dto;

//---[ Imports ]---
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//---[ Class Definition ]---
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RecipeDTO {
	
	private Long id;
	private String name;
	private List<IngredientDTO> ingredients;
	private List<StepDTO> steps;
}
