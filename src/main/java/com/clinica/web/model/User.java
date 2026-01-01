package com.clinica.web.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

@Id
    private String username;
    private String password;
private String email;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

public String getEmail() {return email; }
    public void setEmail(String email) { this.email = email; }
}
