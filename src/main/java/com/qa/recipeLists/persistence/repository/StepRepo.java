package com.qa.recipelists.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.recipelists.persistence.domain.Step;

//---[ Interface Definition ]---
@Repository
public interface StepRepo extends JpaRepository<Step, Long> {

}
