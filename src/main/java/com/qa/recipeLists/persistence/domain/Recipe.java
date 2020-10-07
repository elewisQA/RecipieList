package com.qa.recipeLists.persistence.domain;

//---[ Imports ]---
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//---[ Class Definition ]---
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter					// Let Lombok generate to keep code clean
@ToString
@EqualsAndHashCode
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 	// Unique Identifier
	
	@Column(name = "recipe_name", unique = true)
	private String name;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)	// A recipe has many ingredients
	private List<Ingredient> ingredients = new ArrayList<>();
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)	// A recipe has many steps
	private List<Step> steps = new ArrayList<>();
	
	// Name-only Constructor
	public Recipe(String name) {
		setName(name);
		setIngredients(new ArrayList<>());
		setSteps(new ArrayList<>());
	}
}
