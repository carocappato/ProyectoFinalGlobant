package com.biblioteca.big.repository;

import com.biblioteca.big.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired private UserRepository userRepositoryUnderTest;

    @Test
    @DisplayName("It should check if a user is already in database by comparing the document number with the ones in database")
    void findByDocumentNumberTest() {
        User firstUser = new User("Eugenia", "Fernandez", 38000111L, "eugenia@gmail.com");
        userRepositoryUnderTest.save(firstUser);

        User expected = userRepositoryUnderTest.findByDocumentNumber(38000111L);

        assertThat(expected).isEqualTo(firstUser);
    }
}