package com.biblioteca.big.service;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepositoryUnderTest;

    @Test
    @DisplayName("It should insert a new user")
    public void insertUserTest() {
        //GIVEN
        User user = new User(
                "Margarita",
                "Lopez",
                35643278L,
                "margaritalopez@gmail.com");

        //WHEN
        userRepositoryUnderTest.save(user);

        //THEN
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryUnderTest).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }
}