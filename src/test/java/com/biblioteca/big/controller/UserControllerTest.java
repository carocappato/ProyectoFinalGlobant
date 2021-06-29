package com.biblioteca.big.controller;

import com.biblioteca.big.repository.UserRepository;
import com.biblioteca.big.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp(){
        userServiceUnderTest = new UserService(userRepository);
    }

    @Test
    @DisplayName("It should add a given user in database")
    void insertUserTest() {
    }
}