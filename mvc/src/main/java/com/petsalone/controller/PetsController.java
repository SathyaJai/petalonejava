package com.petsalone.controller;


import java.io.IOException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.petsalone.config.PersistenceJPAConfig;
import com.petsalone.core.Role;
import com.petsalone.exception.PetServiceException;
import com.petsalone.models.AUserInfoAboutPets;
import com.petsalone.models.My_Pet_Class;
import com.petsalone.service.PetsService;
import com.petsalone.util.CommonResponse;
import com.petsalone.util.FileStorageService;

@RestController
@CrossOrigin
public class PetsController {

	@Autowired
	PetsService petsService;
	
	
	@Autowired
	FileStorageService fileStorageService;
	
	
	
	@PostMapping(value = "/uploadmissingdog")
	public @ResponseBody Map<String, Object> uploadimage(@RequestBody My_Pet_Class petclass) throws IOException, PetServiceException {
		return petsService.fileUploadPetDetailsBase64(petclass);
		
		
		
	}
	
	 @GetMapping("/petsimages/{fileName}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	       
		 if(fileName != null) {
			 Resource resource = fileStorageService.loadFileAsResource(fileName);
	
		        // Try to determine file's content type
		        String contentType = null;
		        try {
		            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		        } catch (IOException ex) {
		           System.out.println("Could not determine file type.");
		        }
	
		        // Fallback to the default content type if type could not be determined
		        if(contentType == null) {
		            contentType = "application/octet-stream";
		        }
	
		        return ResponseEntity.ok()
		                .contentType(MediaType.parseMediaType(contentType))
		                 .body(resource);
		 }else {
			 return null;
		 }
	    }
	 
	@GetMapping("/petsimages/fetchall/{petType}")
	public ResponseEntity<List<My_Pet_Class>> fetchPetsImagesByType(@PathVariable int petType, HttpServletRequest request) throws PetServiceException{
		
		List<My_Pet_Class> response =  petsService.fetchAllImages(petType);
		
		
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/petsimages/fetchall")
	public ResponseEntity<CommonResponse<List<My_Pet_Class>>> fetchPetsImages() throws PetServiceException{
		
		
		
		CommonResponse<List<My_Pet_Class>> response = new  CommonResponse<>() ;
		response.setData( petsService.fetchAllImages(null));
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
				
	}
	
	@PostMapping(value = "/petsimages/petsinfobyauser")
	public  ResponseEntity<CommonResponse<Boolean>> updatePetInfoByAUser(@RequestBody AUserInfoAboutPets petsInfoByUser) throws PetServiceException{
		
		CommonResponse<Boolean> response = new  CommonResponse<>() ;
		response.setData(petsService.updatePetInfoByAuser(petsInfoByUser));
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	
}
