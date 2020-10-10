package com.abdelrahman.myreads.MyReads.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "star_value")
    String starValue;

    Long count;

    @ManyToOne
    Book book;



    public Rating(String starValue, Long count, Book book) {
        this.starValue = starValue;
        this.count = count;
        this.book = book;
    }
}
