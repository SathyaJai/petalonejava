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


import com.petsalone.core.Role;
import com.petsalone.service.RoleService;
import com.petsalone.util.CommonResponse;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleController {
	
	
	@Autowired
	RoleService roleService;
	@PostMapping("/addRole")
	public ResponseEntity<CommonResponse<String> > addRole(@RequestBody Role role) {
		
		
		CommonResponse<String> response = new  CommonResponse<>() ;
		response.setData(roleService.addRole(role));
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/fetchroles")
	public ResponseEntity<CommonResponse<List<Role> >>fetchAll(){
		
		
		CommonResponse<List<Role>> response = new  CommonResponse<>() ;
		response.setData(roleService.fetchAllRole());
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/delete/{id}")
	public void deleteRole(@PathVariable Integer id) {
		roleService.deleteRole(id);
	}

}
