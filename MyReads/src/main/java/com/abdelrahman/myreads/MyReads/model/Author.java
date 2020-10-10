package com.abdelrahman.myreads.MyReads.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate born;

    private LocalDate died;

    private String city;

    private String country;


    public Author(String firstName, String lastName, LocalDate born, LocalDate died, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.born = born;
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
                born.equals(author.born) &&
                died.equals(author.died) &&
                city.equals(author.city) &&
                country.equals(author.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, born, died, city, country);
    }
}
