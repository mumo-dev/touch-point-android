package com.example.touchpoint.models;

public class ApiResponse {

    private String message;
    private int id;

    public ApiResponse() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
