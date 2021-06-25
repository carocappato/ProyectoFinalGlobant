package com.biblioteca.big.controller;

import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Object> insertUser (@RequestBody User user)
            throws UserAlreadyExistsException {

        User oldUser = userRepository.findByDocumentNumber(user.getDocumentNumber());

        if (oldUser != null){
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
        userRepository.save(user);
        return ResponseEntity.status(201).build();
    }

}

