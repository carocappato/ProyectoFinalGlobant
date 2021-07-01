package com.biblioteca.big.controller;

import com.biblioteca.big.exception.IllegalEmailFormatException;
import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.User;
import com.biblioteca.big.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired private UserService userService;

    @PostMapping("/create")
    public void createUser(@RequestBody User user) throws UserAlreadyExistsException, IllegalEmailFormatException {
        userService.insertUser(user);

        ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

