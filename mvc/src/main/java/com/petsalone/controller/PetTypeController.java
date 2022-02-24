package com.petsalone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petsalone.models.PetType;
import com.petsalone.service.PetTypeService;
import com.petsalone.util.CommonResponse;

@RestController
@CrossOrigin
@RequestMapping("/petType")
public class PetTypeController {

	@Autowired
	PetTypeService petTypeService;
	
	@PostMapping("/add")
	
	public ResponseEntity<CommonResponse<String> > addRole(@RequestBody PetType pet) {
		
		
		CommonResponse<String> response = new  CommonResponse<>() ;
		response.setData(petTypeService.addPet(pet));
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	
	public ResponseEntity<CommonResponse<List<PetType> >>fetchAll(){
		
		
		CommonResponse<List<PetType>> response = new  CommonResponse<>() ;
		response.setData(petTypeService.fetchAllPet());
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/delete/{id}")
	public void deleteRole(@PathVariable Integer id) {
		petTypeService.deletePet(id);
	}

	
	
}
