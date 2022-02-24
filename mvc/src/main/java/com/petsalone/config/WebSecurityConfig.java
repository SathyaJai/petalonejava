package com.petsalone.config;


import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import com.petsalone.config.*;
import com.petsalone.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements Filter{

	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
    @Bean
   
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}



    
//    @Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		// configure AuthenticationManager so that it knows from where to load
//		// user for matching credentials
//		// Use BCryptPasswordEncoder
//		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
//	}

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }
    
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//            .ignoring()
//            .antMatchers("/h2-console/**");
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	

        http
                
                .formLogin()
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                    .loginPage("/users/login")
                    .loginProcessingUrl("/users/login")
                    .and()
                .logout()
                .permitAll().and()
                .headers().frameOptions().disable();
                
        http.csrf()
        .disable();
       
               
         
              
    	
    	
    	http
		// dont authenticate this particular request
    	.authorizeRequests().antMatchers("/authenticate","/","/h2-console/**","/petsimages/**","/uploadmissingdog").permitAll().
		//.authorizeRequests().antMatchers().permitAll().
		// all other requests need to be authenticated
		anyRequest().authenticated().and().
		// make sure we use stateless session; session won't be used to
		// store user's state.
		exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    	// 	Add a filter to validate the tokens with every request
    	
    	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

		//  response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
//	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//	        response.setHeader("Access-Control-Max-Age", "3600");
//	        response.setHeader("Access-Control-Allow-Headers", "Content-Type,
		 // Access-Control-Allow-Headers, x-requested-with, Authorization");
	   //     response.setHeader("Access-Control-Allow-Credentials" , "true");

        
        response.setHeader("Access-Control-Allow-Origin", "*");
       // response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
        //response.setHeader( "Access-Control-Request-Private-Network", "true" );
      
        response.setHeader("Access-Control-Max-Age", "3600");
//	        response.setHeader("Access-Control-Allow-Origin", "*");
//	        response.setHeader("Access-Control-Allow-Credentials", "true");
//	        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
//	        response.setHeader("Access-Control-Allow-Headers",
//	        		"Origin,Accept, X-Requested-With, Authorization, "
//	        		+ "Content-Type, Access-Control-Request-Method,Access-Control-Request-Headers" 
//	        		);
	        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            chain.doFilter(req, res);
	        }
	}

}