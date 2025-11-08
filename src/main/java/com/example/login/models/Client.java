package com.example.login.models;


public class Client extends User{

    private String firstName;
    private String lastName;
    private String id;
    private String phone;
    private String accountType;

    public Client(){}

    public Client(String email, String password, String firstName,
                  String lastName, String id, String phone, String accountType) {
        super(email, password, 3);
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.phone = phone;
        this.accountType= accountType;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Nombres='" + firstName + '\'' +
                ", apellidos='" + lastName + '\'' +
                ", documento='" + id + '\'' +
                ", telefono='" + phone + '\'' +
                ", Tipo de cuenta='" + accountType + '\'' +
                '}';
    }
}
