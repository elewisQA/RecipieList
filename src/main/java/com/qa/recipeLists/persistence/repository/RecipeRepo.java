package com.qa.recipelists.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.recipelists.persistence.domain.Recipe;

//---[ Interface Definition ]---
@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

}
