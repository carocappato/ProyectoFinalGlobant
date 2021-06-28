package com.biblioteca.big.exception;

import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class IllegalEmailFormatExceptionTest {
    @Mock
    private UserRepository userRepoUnderTest;

    @Test
    @DisplayName("It should throw an exception if the email format is not valid")
    public void shouldThrowIllegalEmailFormatException() {
        //GIVEN
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDocumentNumber(33444555L);
        user.setEmail("johndoe");

        userRepoUnderTest.save(user);

        //WHEN
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepoUnderTest).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        //THEN
        Assertions.assertThrows(IllegalEmailFormatException.class, () -> {
            if(!capturedUser.getEmail().matches("^(.+)@(.+)$")) {
                throw new IllegalEmailFormatException("Formato de mail no válido");
            }
        });
    }
}
