package com.biblioteca.big.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

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

    @Column(name = "publish_year")
    private int publishYear;

    @Column(name = "book_status", length = 50)
    private String bookStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservationId;

    public Book() { }

    public Book(String title, String author, int publishYear, String bookStatus, Reservation reservationId) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
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
        this.title = title.toUpperCase();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author.toUpperCase();
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus.toUpperCase();
    }

    public Reservation getReservation() {
        return reservationId;
    }

    public void setReservation(Reservation reservation) {
        this.reservationId = reservation;
    }
}
