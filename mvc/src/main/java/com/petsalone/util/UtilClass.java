package com.petsalone.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UtilClass {
	
	public  Timestamp getCurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public String getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return auth.getName();
	}
	

	}
