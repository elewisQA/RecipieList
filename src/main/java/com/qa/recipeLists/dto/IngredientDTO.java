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
public class IngredientDTO {
	
	private Long id;
	private String name;
	private String unit;
	private Float quantity;

}
