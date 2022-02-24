package com.petsalone.models;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.petsalone.core.AbstractEntity;

import lombok.Data;
@Data
@Entity
public class AUserInfoAboutPets extends  AbstractEntity{
	
	private String aname;
	private Integer petid;
	private String ainfo;
	private String acontactnumber;
//	@OneToOne(orphanRemoval = true)
//    @JoinColumn(name = "petid")
//    private My_Pet_Class pet;
	private Timestamp created_date;
	private Timestamp updated_date;
	
}
