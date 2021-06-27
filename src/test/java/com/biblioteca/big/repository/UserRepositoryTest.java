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
        //given
        User user = new User("Eugenia", "Fernandez",
                38000111, "EUGENIA@GMAIL.COM");
        User secondUser = new User("Martina", "Gomez",
                40999888, "MARTINA@GMAIL.COM");
        userRepositoryUnderTest.save(user);
        userRepositoryUnderTest.save(secondUser);
        //when
        User expected = userRepositoryUnderTest.findByDocumentNumber(38000111);
        //then
        assertThat(expected).isEqualTo(user);
    }
}