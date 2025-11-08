package com.example.login.models;

public class Cashier extends User{

    private String id;
    private String Shift;

    public Cashier(){}

    public Cashier(String email, String password, String id, String Shift) {
        super (email, password, 2);
        this.id = id;
        this.Shift= Shift;
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
