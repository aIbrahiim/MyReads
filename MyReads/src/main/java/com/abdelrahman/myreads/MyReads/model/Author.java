package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
public class Author extends Person{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    private LocalDate died;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "author_books",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    @JsonIgnoreProperties
    private Set<Book> books = new HashSet<>();

    public Author(String firstName, String lastName, LocalDate birth, LocalDate died, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.died = died;
        this.city = city;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) &&
                lastName.equals(author.lastName) &&
                birth.equals(author.birth) &&
                died.equals(author.died) &&
                city.equals(author.city) &&
                country.equals(author.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birth, died, city, country);
    }
}
