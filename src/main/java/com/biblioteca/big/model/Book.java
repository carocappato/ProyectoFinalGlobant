package com.biblioteca.big.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "editorial_date")
    private Date editorialDate;

    @Column(name = "book_status", length = 50)
    private String bookStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservationId;

    public Book() { }

    public Book(Long id, String title, String author, Date editorialDate, String bookStatus, Reservation reservationId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.editorialDate = editorialDate;
        this.bookStatus = bookStatus;
        this.reservationId = reservationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getEditorialDate() {
        return editorialDate;
    }

    public void setEditorialDate(Date editorialDate) {
        this.editorialDate = editorialDate;
    }

    public String getStatus() {
        return bookStatus;
    }

    public void setStatus(String status) {
        this.bookStatus = status;
    }

    public Reservation getReservation() {
        return reservationId;
    }

    public void setReservation(Reservation reservation) {
        this.reservationId = reservation;
    }
}
