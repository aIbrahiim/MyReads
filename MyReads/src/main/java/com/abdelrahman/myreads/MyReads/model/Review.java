package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Data
@JsonIgnoreProperties(value = {"createdAt"},
        allowGetters = true)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String body;

    @Nullable
    private Long parentId;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public Review(String body, @Nullable Long parentId, Double rating, User user, Book book, LocalDateTime createdAt) {
        this.body = body;
        this.parentId = parentId;
        this.rating = rating;
        this.user = user;
        this.book = book;
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
