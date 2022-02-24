package com.petsalone.core;

import java.sql.Timestamp;

import javax.persistence.Entity;

import lombok.Data;
@Data
@Entity
public class Role extends AbstractEntity {

    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private Timestamp created_date;
    private Timestamp updated_date;
    private String created_by;

  
}
