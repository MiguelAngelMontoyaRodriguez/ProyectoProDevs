package com.example.login.models;

public class Admin extends User{
    private String name;
    private String phone;
    private String id;

    public Admin(){}

    public Admin(String email, String password,String name, String phone,String id ){
        super(email, password, 1);
        this.name=name;
        this.phone=phone;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
