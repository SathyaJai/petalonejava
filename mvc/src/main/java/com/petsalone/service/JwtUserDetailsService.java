package com.petsalone.service;

import java.util.HashSet;
import java.util.Set;

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

@Service
public class JwtUserDetailsService  implements UserDetailsService{

	
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
	
	 public User getUserRoleByUserName(String userName) {
	    	User user = userRepository.findByUsername(userName);	
	    	User user1 = new User();
	        user1.setUsername(user.getUsername());
	        user1.setRoles(user.getRoles());
	        return user1;
	    }

}
