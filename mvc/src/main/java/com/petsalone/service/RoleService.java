package com.petsalone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petsalone.core.Role;
import com.petsalone.repository.RoleRepository;
import com.petsalone.util.UtilClass;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UtilClass utilClass;
	public String addRole( Role role) {
		
		Optional<Role> roleOptional =  Optional.of(role);
		if(roleOptional.isPresent()) {
			 role = roleOptional.get();
			 role.setCreated_date(utilClass.getCurrentTimeStamp());
			 role.setUpdated_date(utilClass.getCurrentTimeStamp());
			 role.setCreated_by(utilClass.getLoggedInUser());
			roleRepository.save(role);
			
			return "role added";
		}else {
			return "please try again later";
		}
	}
	
	
	public List<Role> fetchAllRole(){
		return roleRepository.findAll();
	}
	public void deleteRole(Integer id) {
		 roleRepository.deleteById(id);
	}
	
}
