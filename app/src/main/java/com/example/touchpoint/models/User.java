package com.example.touchpoint.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String password;
    private String email;

    public User() {
    }

    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }

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
}
