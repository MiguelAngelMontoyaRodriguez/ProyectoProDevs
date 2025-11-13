package com.example.login.models;

public class Cashier extends User{

    private String name;
    private String id;
    private String Shift;

    public Cashier(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cashier(String email, String password, String name, String id, String Shift) {
        super (email, password, 2);
        this.id = id;
        this.Shift= Shift;
        this.name=name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "id='" + id + '\'' +
                ", Shift='" + Shift + '\'' +
                '}';
    }
}
