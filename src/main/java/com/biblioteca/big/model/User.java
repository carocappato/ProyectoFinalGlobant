package com.biblioteca.big.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "document_number", nullable = false)
    private Long documentNumber;

    @Column(name = "email", length = 100, nullable = false)
    @Pattern(regexp = "^(.+)@(.+)$", message = "Formato de mail no válido")
    private String email;

    public User(){ }

    public User(String firstName, String lastName, Long documentNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.toUpperCase();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.toUpperCase();
    }

    public String getLastName() {
        return lastName.toUpperCase();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }

    public Long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Long documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEmail() {
        return email.toUpperCase();
    }

    public void setEmail(String email) {
        this.email = email.toUpperCase();
    }

}
