package com.biblioteca.big.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date endDate;

    @OneToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonProperty( value = "book", access = JsonProperty.Access.WRITE_ONLY)
    private Book book;

    public Reservation(){ }

    public Reservation(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook(){ return book; }

    public void setBook(Book book){ this.book = book; }
}
