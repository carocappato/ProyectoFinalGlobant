package com.biblioteca.big.exception;

import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.UserRepository;
import com.biblioteca.big.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class IllegalEmailFormatExceptionTest {
    @Mock
    private UserRepository userRepoUnderTest;

    @Test
    @Disabled
    @DisplayName("It should throw an exception if the email format is not valid")
    public void shouldThrowIllegalEmailFormatException() {
        UserService userService = new UserService();

        User user = new User(
                "John",
                "Doe",
                33444555L,
                "johndoe");


        userRepoUnderTest.save(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepoUnderTest).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        Assertions.assertThrows(IllegalEmailFormatException.class, () -> {
            userService.insertUser(capturedUser);
        });

        //TODO VER POR QUE NO FUNCIONA, YA PROBE DE VARIAS FORMAS

    }
}
