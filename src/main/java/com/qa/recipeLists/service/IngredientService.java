package com.qa.recipeLists.service;

//---[ Imports ]---
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.recipeLists.dto.IngredientDTO;
import com.qa.recipeLists.exception.IngredientNotFoundException;
import com.qa.recipeLists.persistence.domain.Ingredient;
import com.qa.recipeLists.persistence.repository.IngredientRepo;
import com.qa.recipeLists.utils.RecipeListsUtils;

//---[ Service Definition ]---
@Service
public class IngredientService {

	//--[ Service Variables ]--
	private IngredientRepo repo;
	
	private ModelMapper mapper;
	
	//--[ Service Constructor ]--
	@Autowired // Let Spring wire-up the class
	public IngredientService(IngredientRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	//--[ Service Methods ]--
	private IngredientDTO mapToDTO(Ingredient ingredient) {
		return this.mapper.map(ingredient, IngredientDTO.class);
	}
	
	// CRUD Methods
	public IngredientDTO create(Ingredient ingredient) {
		Ingredient created = this.repo.save(ingredient);
		return this.mapToDTO(created);
	}
	
	public List<IngredientDTO> read() {	// Read-All Method
		List<Ingredient> found = this.repo.findAll();
		return found.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	public IngredientDTO read(Long id) {	// Read-by-id Method
		Ingredient found = this.repo.findById(id)
				.orElseThrow(IngredientNotFoundException::new);
		return this.mapToDTO(found);
	}
	
	public IngredientDTO update(IngredientDTO dto, Long id) {
		Ingredient toUpdate = this.repo.findById(id)
				.orElseThrow(IngredientNotFoundException::new);
		RecipeListsUtils.mergeNotNull(dto, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	public Boolean delete(Long id) {
		if (!this.repo.existsById(id)) {
			throw new IngredientNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
