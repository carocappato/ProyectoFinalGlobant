package com.biblioteca.big.repository;

import com.biblioteca.big.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryUnderTest;

    @Test
    void itShouldFindByDocumentNumber() {
        //given
        User user = new User("Eugenia", "Fernandez",
                38000111, "eugenia@gmail.com");
        User secondUser = new User("Martina", "Gomez",
                40999888, "martina@gmail.com");
        userRepositoryUnderTest.save(user);
        userRepositoryUnderTest.save(secondUser);
        //when
        User expected = userRepositoryUnderTest.findByDocumentNumber(38000111);
        //then
        assertThat(expected).isEqualTo(user);
    }
}