package com.berk.userauthservice.dto;

public class AuthResponse {

    private String username;

    public AuthResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
