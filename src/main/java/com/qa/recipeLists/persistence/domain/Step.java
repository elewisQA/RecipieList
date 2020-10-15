package com.qa.recipelists.persistence.domain;

//---[ Imports ]---
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//---[ Class Definition ]---
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter					// Let Lombok generate to keep code clean
@ToString
@EqualsAndHashCode
public class Step {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	// Unique identifier
	
	@Column
	@Size(min = 1, max = 100)
	private String name;
	
	@Column
	@Size(min = 1, max = 5000)
	private String description;
	
	@ManyToOne	// There are many ingredients in a recipe
	private Recipe recipe;
	
	public Step(String name, String description) {
		super();
		this.setName(name);
		this.setDescription(description);
	}
}
