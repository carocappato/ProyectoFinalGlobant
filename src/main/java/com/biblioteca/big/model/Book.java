package com.biblioteca.big.model;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "author", length = 100, nullable = false)
    private String author;

    @Column(name = "publish_year", nullable = false)
    private int publishYear;

    @Column(name = "book_status", length = 50, nullable = false)
    private String bookStatus;

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservationId;

    public Book() { }

    public Book(String title,
                String author,
                int publishYear,
                String bookStatus,
                Reservation reservationId) {
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
        return title.toUpperCase();
    }

    public void setTitle(String title) {
        this.title = title.toUpperCase();
    }

    public String getAuthor() {
        return author.toUpperCase();
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
        return bookStatus.toUpperCase();
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
