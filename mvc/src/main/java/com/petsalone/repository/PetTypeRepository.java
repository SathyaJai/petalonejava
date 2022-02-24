package com.petsalone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petsalone.models.PetType;
@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {

}
