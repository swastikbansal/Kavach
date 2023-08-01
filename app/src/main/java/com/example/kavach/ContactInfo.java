package com.example.kavach;

import android.util.Log;

public class ContactInfo {
    private int id; // Unique ID for each contact
    private String name;
    private String number;

    public ContactInfo(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public ContactInfo(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;

    }

    // Add getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}