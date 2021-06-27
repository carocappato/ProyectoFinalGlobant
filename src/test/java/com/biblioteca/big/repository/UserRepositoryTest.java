package com.biblioteca.big.repository;

import com.biblioteca.big.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryUnderTest;

    @Test
    void itShouldFindByDocumentNumber() {
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