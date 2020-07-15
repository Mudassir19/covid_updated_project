package com.xebia.covid_app.models;

public class Payload {
    private String jwt;

    public Payload(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
