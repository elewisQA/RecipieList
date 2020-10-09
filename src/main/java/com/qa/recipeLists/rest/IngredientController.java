package com.qa.recipeLists.rest;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qa.recipeLists.dto.IngredientDTO;
import com.qa.recipeLists.persistence.domain.Ingredient;
import com.qa.recipeLists.service.IngredientService;

//---[ Controller Definition ]---
@RestController
@RequestMapping("/ingredient") // Map to '/Ingredient' destination of URL
public class IngredientController {
	
	//--[ Class Variables ]--
	private IngredientService service;
	
	//--[ Class Constructor ]--
	@Autowired
	public IngredientController(IngredientService service) {
		super();
		this.service = service;
	}
	
	//--[ Controller Mappings ]--
	@PostMapping("/create")
	public ResponseEntity<IngredientDTO> create(@RequestBody Ingredient Ingredient) {
		IngredientDTO created = this.service.create(Ingredient);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/read")
	public ResponseEntity<List<IngredientDTO>> read() {
		return ResponseEntity.ok(this.service.read());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<IngredientDTO> read(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<IngredientDTO> update(@PathVariable Long id, @RequestBody IngredientDTO dto) {
		System.out.println("H\nH\nH");
		System.out.println("ID: " + id);
		System.out.println("Obj: " + dto.toString());
		return new ResponseEntity<>(this.service.update(dto, id), HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<IngredientDTO> delete(@PathVariable Long id) {
		return this.service.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
