package com.qa.recipelists.rest;

//---[ Imports ]---
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.recipelists.dto.StepDTO;
import com.qa.recipelists.persistence.domain.Step;
import com.qa.recipelists.service.StepService;

//---[ Controller Definition ]---
@RestController
@CrossOrigin
@RequestMapping("/step") // Map to '/step' destination of URL
public class StepController {
	
	//--[ Class Variables ]--
	private StepService service;
	
	//--[ Class Constructor ]--
	@Autowired
	public StepController(StepService service) {
		super();
		this.service = service;
	}
	
	//--[ Controller Mappings ]--
	@PostMapping("/create")
	public ResponseEntity<StepDTO> create(@RequestBody Step step) {
		StepDTO created = this.service.create(step);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<StepDTO>> read() {
		return ResponseEntity.ok(this.service.read());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<StepDTO> read(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<StepDTO> update(@PathVariable Long id, @RequestBody StepDTO dto) {
		return new ResponseEntity<>(this.service.update(dto, id), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<StepDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
