package com.biblioteca.big.service;

import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //POST USER
    public void insertUser (@RequestBody User user)
            throws UserAlreadyExistsException, ArithmeticException {
        User olderUser = userRepository.findByDocumentNumber(user.getDocumentNumber());
        if (olderUser != null){
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
        userRepository.save(user);
    }
}
