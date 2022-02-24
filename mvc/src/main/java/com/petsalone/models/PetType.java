package com.petsalone.models;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.petsalone.core.AbstractEntity;

import lombok.Data;

@Data
@Entity
public class PetType extends AbstractEntity{

	private String petName;
	
	 private Timestamp created_date;
	    private Timestamp updated_date;
	    private String created_by;

}
