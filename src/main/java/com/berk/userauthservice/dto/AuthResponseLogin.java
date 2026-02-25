package com.berk.userauthservice.dto;

public class AuthResponseLogin {

    private String token;

    public AuthResponseLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
