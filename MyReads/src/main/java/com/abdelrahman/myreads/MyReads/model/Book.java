package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String title;

    private String isbn;

    private Integer numberOfPages;

    @Enumerated(EnumType.STRING)
    private Genre bookGenre;

    @Min(0)
    private Double rating;

    private String coverUUID;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    @JsonIgnore
    Set<Author> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    @JsonIgnore
    Set<Shelf> bookShelves = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}
