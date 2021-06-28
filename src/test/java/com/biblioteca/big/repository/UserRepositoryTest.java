package com.biblioteca.big.repository;

import com.biblioteca.big.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepositoryUnderTest;

    @Test
    @DisplayName("It should check if a user is already in database by comparing the document number with the ones in database")
    void findByDocumentNumberTest() {
        //GIVEN
        User firstUser = new User(
                "Eugenia",
                "Fernandez",
                38000111L,
                "eugenia@gmail.com");

        User secondUser = new User(
                "Martina",
                "Gomez",
                40999888L,
                "martina@gmail.com");
        userRepositoryUnderTest.save(firstUser);
        userRepositoryUnderTest.save(secondUser);

        //WHEN
        User expected = userRepositoryUnderTest.findByDocumentNumber(38000111L);

        //THEN
        assertThat(expected).isEqualTo(firstUser);
    }
}