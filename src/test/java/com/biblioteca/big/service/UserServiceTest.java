package com.biblioteca.big.service;

import com.biblioteca.big.exception.IllegalEmailFormatException;
import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Captor
    private ArgumentCaptor<Long> idDocumentCaptor;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    @DisplayName("It should insert the given user in database")
    public void insertUserTest() throws IllegalEmailFormatException, UserAlreadyExistsException {
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        userServiceUnderTest.insertUser(user);

        userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    @DisplayName("It should throw an exception if the email format is not valid")
    public void illegalEmailFormatExceptionTest() {
        User user = new User("John", "Doe", 33444555L,"johndoe");

        Assertions.assertThrows(IllegalEmailFormatException.class, () -> {
            userServiceUnderTest.insertUser(user);
        });

        verify(userRepository).findByDocumentNumber(idDocumentCaptor.capture());
        assertThat(idDocumentCaptor.getValue()).isEqualTo(user.getDocumentNumber());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("It should throw an exception if the user already exists")
    public void userAlreadyExistsExceptionTest() {
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");

        given(userRepository.findByDocumentNumber(user.getDocumentNumber())).willReturn(user);

        assertThatThrownBy(() -> userServiceUnderTest.insertUser(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("El usuario ya existe");

        verify(userRepository, never()).save(any());
    }

}