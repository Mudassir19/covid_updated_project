package com.xebia.covid_app.service;

import java.util.List;
import java.util.Optional;

import com.xebia.covid_app.entities.User;

public interface UserService {
    Optional<User> findUser(String username);

    List<User> findAllUsers();

}
