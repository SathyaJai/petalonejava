package com.petsalone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.petsalone.core.*;
import com.petsalone.models.*;
import com.petsalone.repository.UserRepository;
import com.petsalone.service.JwtUserDetailsService;
import com.petsalone.util.CommonResponse;

import java.util.*;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin("http://localhost:4200")
public class UsersController implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
	private ApplicationContext applicationContext;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    JwtUserDetailsService jwtUserDetailsService;
    public UserDetailsService getUserDetailsImpl(String key) {
		return (UserDetailsService) applicationContext.getBean(key);
	}
    
    @GetMapping("/users/login")
    public String showLoginForm(LoginModel loginModel, Model model, String error, String logout) {

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "users/login";
    }
    
   
    
    @GetMapping("/getuser/{username}")
    public ResponseEntity<CommonResponse<User>> getUsersRoleDetailsByName(@PathVariable String userName) {
    	
        
        CommonResponse<User> response = new  CommonResponse<>() ;
		response.setData(jwtUserDetailsService.getUserRoleByUserName(userName));
		response.setSuccess(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    
    
}


