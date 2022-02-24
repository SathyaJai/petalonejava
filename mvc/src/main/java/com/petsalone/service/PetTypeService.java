package com.petsalone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsalone.core.Role;
import com.petsalone.models.PetType;
import com.petsalone.repository.PetTypeRepository;
import com.petsalone.util.UtilClass;

@Service
public class PetTypeService {

	
	@Autowired
	UtilClass utilClass;
	
	@Autowired
	PetTypeRepository  petTypeRepository;
	public String addPet( PetType pettype) {
		
		Optional<PetType> roleOptional =  Optional.of(pettype);
		if(roleOptional.isPresent()) {
			pettype = roleOptional.get();
			pettype.setCreated_date(utilClass.getCurrentTimeStamp());
			pettype.setUpdated_date(utilClass.getCurrentTimeStamp());
			pettype.setCreated_by(utilClass.getLoggedInUser());
			petTypeRepository.save(pettype);
			
			return "pet added";
		}else {
			return "please try again later";
		}
	}
	
	
	public List<PetType> fetchAllPet(){
		return petTypeRepository.findAll();
	}
	public void deletePet(Integer id) {
		petTypeRepository.deleteById(id);
	}

}
