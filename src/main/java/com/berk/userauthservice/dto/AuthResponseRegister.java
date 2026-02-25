package com.berk.userauthservice.dto;

public class AuthResponseRegister {

    private String username;

    public AuthResponseRegister(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
