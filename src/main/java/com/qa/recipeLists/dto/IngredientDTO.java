package com.qa.recipelists.dto;

//---[ Imports ]---
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
public class IngredientDTO {
	
	private Long id;
	private String name;
	private String unit;
	private Float quantity;

}
