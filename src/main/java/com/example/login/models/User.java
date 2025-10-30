package com.example.login.models;

public class User {

    private String email;
    private String password;
    private String role;

    public User(){
    }

    public User (String email, String password, String role){
        this.email= email;
        this.role= role;
        this.password= password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
