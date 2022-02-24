package com.petsalone.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.petsalone.constants.ConstantsClass;
import com.petsalone.exception.PetServiceException;
import com.petsalone.models.AUserInfoAboutPets;
import com.petsalone.models.My_Pet_Class;
import com.petsalone.repository.AUserInfoAboutPetsRepository;
import com.petsalone.repository.PetsRepository;
import com.petsalone.util.FileStorageService;
import com.petsalone.util.UtilClass;



@Service

public class PetsService {
	
	
	@Autowired
	AUserInfoAboutPetsRepository aUserInfoAboutPetsRepository;
	@Autowired
	FileStorageService fileStorageService;
	
	
	@Autowired
	UtilClass utilClass;
	@Value("${server.port}")
	String port;
	@Autowired
	PetsRepository petsRepository;
	public Map<String, Object> fileUploadPetDetails(MultipartFile file, My_Pet_Class petClass) throws IOException{
		Map<String, Object> result = new HashMap<>();
		
		String filePath = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/petsimages/")
                .path(filePath)
                .toUriString();
		if(!filePath.equalsIgnoreCase("error")) {
			
			petClass.setImagePath(fileDownloadUri);
			petClass.setCreated_date(utilClass.getCurrentTimeStamp());
			petClass.setUpdated_date(utilClass.getCurrentTimeStamp());
			petClass.setCreated_by(utilClass.getLoggedInUser());
		//	petClass.setMissingSince(utilClass.dateFormat(petClass.getMissingDate()));
			petsRepository.save(petClass);
			result.put("uploadStatus", true);
			result.put("url",fileDownloadUri);
			
		}else {
			result.put("uploadStatus", false);
		}
		
		return result;
		
		
		
	}
	
	
	
	public Map<String, Object> fileUploadPetDetailsBase64( My_Pet_Class petClass) throws PetServiceException{
		Map<String, Object> result = new HashMap<>();
		if( petClass.getBase64Value() !=null) {
			String[] image = petClass.getBase64Value().split(",");
			
			String filePath;
			try {
				filePath = fileStorageService.storeImage(image[1], petClass.getName());
				String imageUurl = ConstantsClass.DOMAIN_NAME+port+"/petsimages/"+ filePath;
				if(!filePath.equalsIgnoreCase("error")) {
					
					petClass.setImagePath(filePath);
					petClass.setCreated_date(utilClass.getCurrentTimeStamp());
					petClass.setUpdated_date(utilClass.getCurrentTimeStamp());
					petClass.setCreated_by(utilClass.getLoggedInUser());
					petClass.setMissingSince(petClass.getMissingDate());
					petsRepository.save(petClass);
					result.put("uploadStatus", true);
					result.put("url",imageUurl);
					
				}else {
					result.put("uploadStatus", false);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 throw new PetServiceException(" File not stored");
			}
			
			
		}
		
		return result;
		
		
		
	}
	
	
	public List<My_Pet_Class> fetchAllImages(Integer petType) throws PetServiceException{
		List<My_Pet_Class> listMissingPets = new ArrayList<>();
		
		
		if(petType !=null) {
			listMissingPets = petsRepository.fetchAllMissingDogByPetType(petType);
			
			
		}else {
			listMissingPets= petsRepository.fetchAllMissingDog();
		}
		
		listMissingPets.forEach(e -> e.setImagePath(ConstantsClass.DOMAIN_NAME+port+"/petsimages/"+e.getImagePath()));
		
		return listMissingPets;
	}
	
		public Boolean updatePetInfoByAuser(AUserInfoAboutPets petInfo)  throws PetServiceException{
		
		try {
			aUserInfoAboutPetsRepository.save(petInfo);
			return true;
			
		}catch(Exception en) {
		   throw new PetServiceException(" Pet information is not updated");
		}
	}
	
	
	
	
	}
