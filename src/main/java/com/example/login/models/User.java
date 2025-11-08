package com.example.login.models;

public abstract class User {

    private String email;
    private String password;
    private int role;

    public User(){
    }

    public User (String email, String password, int role){
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

//    public abstract void implementRol();
}
