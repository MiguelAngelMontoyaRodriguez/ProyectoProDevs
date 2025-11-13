package com.example.login.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Movements {
    private String clientId;
    private String type;
    private double amount;
    private LocalDateTime date;

    public Movements(){}

    public Movements(String clientId, String type, double amount) {
        this.clientId = clientId;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getFormattedDate() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(fmt);
    }

    @Override
    public String toString() {
        return "[" + getFormattedDate() + "] " + clientId + " " + type + " de $" + amount + ")";
    }
}
