package com.xebia.covid_app.service;

import com.xebia.covid_app.entities.User;
import com.xebia.covid_app.util.EmailSend;

import java.util.Optional;
import java.util.Random;

public interface EmailService {
    void sendEmail(String username);

    int getRandomNumberUsingNextInt();
}
