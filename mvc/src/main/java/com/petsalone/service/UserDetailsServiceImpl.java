package com.petsalone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.petsalone.core.Role;
import com.petsalone.core.User;
import com.petsalone.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;


public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository ;
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        

        User dbUser = userRepository.findByUsername(username);
        
       
        if (dbUser == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : dbUser.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(dbUser.getUsername(), dbUser.getPassword(), grantedAuthorities);
    }
    
    
   
}
