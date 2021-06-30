package com.biblioteca.big.controller;

import com.biblioteca.big.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    @DisplayName("It should add a given user in database")
    void insertUserTest() throws Exception {
        User user = new User("John", "Doe", 11222333L ,"johndoe@gmail.com");

        given(userController.newUser(user)).willReturn(ResponseEntity.status(201).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/users"));
    }
}