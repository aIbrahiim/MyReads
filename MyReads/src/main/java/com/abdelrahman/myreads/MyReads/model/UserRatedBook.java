package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class UserRatedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    Integer rate;


    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime ratedAt;

    public UserRatedBook(User user, Book book, Integer rate, LocalDateTime ratedAt) {
        this.user = user;
        this.book = book;
        this.rate = rate;
        this.ratedAt = ratedAt;
    }
}
