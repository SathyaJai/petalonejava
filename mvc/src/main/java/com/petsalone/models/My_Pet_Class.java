package com.petsalone.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.persistence.metamodel.StaticMetamodel;

import com.petsalone.core.AbstractEntity;

import lombok.Data;

@Data
@Entity

public class My_Pet_Class extends AbstractEntity {

    // the name
	
    private String name;

    // missing since
    private String missingSince;
    
    

    // type
    // 1 = Cat, 2 = Dog, 3 = Hamster, 4 = Bird, 5 = Rabbit, 6 = Fish, 7 = Lizard, 8 = Horse, 9 = Gerbil, 10 = Tortoise
    private Integer petType;

   
    private String imagePath;
    
    private Timestamp created_date;
    private Timestamp updated_date;
    private String created_by;
    private String petsOwner;
    private String petsOwnerContactNumber;
    @Transient
    private String missingDate ;
    private String aboutPet;
    private String email;
    @Transient
    private String base64Value;
    
  @OneToMany(mappedBy="petid")
  private Set<AUserInfoAboutPets> lstUserInfo;
   
    
   
   	

	
}