package com.qa.recipelists.service;

//---[ Imports ]---
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.recipelists.dto.StepDTO;
import com.qa.recipelists.exception.StepNotFoundException;
import com.qa.recipelists.persistence.domain.Step;
import com.qa.recipelists.persistence.repository.StepRepo;
import com.qa.recipelists.utils.RecipeListsUtils;

//---[ Service Definition ]---
@Service
public class StepService {

	//--[ Service Variables ]--
	private StepRepo repo;
	
	private ModelMapper mapper;
	
	//--[ Service Constructor ]--
	@Autowired // Let Spring wire-up the class
	public StepService(StepRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	//--[ Service Methods ]--
	private Step mapFromDTO(StepDTO stepDTO) {
		return this.mapper.map(stepDTO, Step.class);
	}
	private StepDTO mapToDTO(Step step) {
		return this.mapper.map(step, StepDTO.class);
	}
	
	// CRUD Methods
	public StepDTO create(Step step) {
		Step created = this.repo.save(step);
		return this.mapToDTO(created);
	}
	
	public List<StepDTO> read() {	// Read-All Method
		List<Step> found = this.repo.findAll();
		return found.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	public StepDTO read(Long id) {	// Read-by-id Method
		Step found = this.repo.findById(id)
				.orElseThrow(StepNotFoundException::new);
		return this.mapToDTO(found);
	}
	
	public StepDTO update(StepDTO dto, Long id) {
		Step toUpdate = this.repo.findById(id)
				.orElseThrow(StepNotFoundException::new);
		RecipeListsUtils.mergeNotNull(dto, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	public Boolean delete(Long id) {
		if (!this.repo.existsById(id)) {
			throw new StepNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}
