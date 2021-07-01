package com.biblioteca.big.controller;

import com.biblioteca.big.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private UserController userController;

    @Test
    @DisplayName("It should add a given user in database")
    public void insertUserTest() throws Exception {
        User user = new User("John", "Doe", 11222333L ,"johndoe@gmail.com");
        userController.createUser(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users"));
    }
}