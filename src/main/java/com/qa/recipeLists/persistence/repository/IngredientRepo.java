package com.qa.recipeLists.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.recipeLists.persistence.domain.Ingredient;

//---[ Interface Definition ]---
@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

}
