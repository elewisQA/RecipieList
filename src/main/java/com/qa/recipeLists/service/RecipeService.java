package com.qa.recipelists.service;

//---[ Imports ]---
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.recipelists.dto.RecipeDTO;
import com.qa.recipelists.exception.RecipeNotFoundException;
import com.qa.recipelists.persistence.domain.Recipe;
import com.qa.recipelists.persistence.repository.RecipeRepo;
import com.qa.recipelists.utils.RecipeListsUtils;

//---[ Service Definition ]---
@Service
public class RecipeService {

	//--[ Service Variables ]--
	private RecipeRepo repo;
	
	private ModelMapper mapper;
	
	//--[ Service Constructor ]--
	@Autowired // Let Spring wire-up the class 
	public RecipeService(RecipeRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	//--[ Service Methods ]--	
	private RecipeDTO mapToDTO(Recipe recipe) {
		return this.mapper.map(recipe, RecipeDTO.class);
	}
	
	// CRUD Methods
	public RecipeDTO create(Recipe recipe) {
		Recipe created = this.repo.save(recipe);
		return this.mapToDTO(created);
	}
	
	public List<RecipeDTO> read() {	// Read-All Method
		List<Recipe> found = this.repo.findAll();
		return found.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	public RecipeDTO read(Long id) { // Read-by-id Method
		Recipe found = this.repo.findById(id)
				.orElseThrow(RecipeNotFoundException::new);
		return this.mapToDTO(found);
	}
	
	public RecipeDTO update(RecipeDTO dto, Long id) {
		Recipe toUpdate = this.repo.findById(id)
				.orElseThrow(RecipeNotFoundException::new);
		toUpdate = RecipeListsUtils.mapRecipeFromDTO(dto, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
		
	}
	
	public Boolean delete(Long id) {
		if(!this.repo.existsById(id)) {
			throw new RecipeNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
