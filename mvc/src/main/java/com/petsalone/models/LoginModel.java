package com.petsalone.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginModel {

    @NotNull
    @Size(min=2, max=30)
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }
    public String toString() {
        return "Login(Username: " + this.username + ", Password" + this.password + ")";
    }
}