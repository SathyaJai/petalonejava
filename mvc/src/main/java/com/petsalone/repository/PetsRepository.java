package com.petsalone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.petsalone.models.My_Pet_Class;

public interface PetsRepository extends JpaRepository<My_Pet_Class, Integer> {

	
	@Query("select pets from My_Pet_Class pets order by pets.created_date desc")
	List<My_Pet_Class> fetchAllMissingDog();
	
	
	@Query("select pets from My_Pet_Class pets where pets.petType=?1 order by pets.created_date desc")
	List<My_Pet_Class> fetchAllMissingDogByPetType(@Param("petType") Integer petType );
}
