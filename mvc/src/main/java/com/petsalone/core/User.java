package com.petsalone.core;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
public class User extends AbstractEntity {

	
    private String username;
	
    private String password;
	
    private String email;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles;

    private Timestamp created_date;
    private Timestamp updated_date;
    private String created_by;
   }
