package com.biblioteca.big.repository;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.documentNumber = ?1")
    User findByDocumentNumber(Long documentNumber);

}
