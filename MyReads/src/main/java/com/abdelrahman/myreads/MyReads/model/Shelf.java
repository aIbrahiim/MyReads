package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "shelves")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    boolean predefined;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    User user;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_shelves",
            joinColumns = {@JoinColumn(name = "shelf_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @JsonIgnoreProperties
    Set<Book> books = new HashSet<>();

    public Shelf(boolean predefined, String name, User user) {
        this.predefined = predefined;
        this.name = name;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return name.equals(shelf.name) &&
                user.equals(shelf.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}
