package com.xebia.covid_app.service.impl;

import com.xebia.covid_app.entities.User;
import com.xebia.covid_app.repository.UserRepository;
import com.xebia.covid_app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public Optional<User> findUser(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

}
