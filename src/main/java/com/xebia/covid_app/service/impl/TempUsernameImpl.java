package com.xebia.covid_app.service.impl;

import org.springframework.stereotype.Service;

import com.xebia.covid_app.service.TempUsername;

@Service
public class TempUsernameImpl implements TempUsername {
    private String username;

    public TempUsernameImpl() {
    }

    public TempUsernameImpl(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
