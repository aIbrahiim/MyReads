package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
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

    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    User user;

    @Setter
    @Getter
    private Integer count;

    public Shelf(boolean predefined, String name, LocalDateTime createdAt, User user, Integer count) {
        this.predefined = predefined;
        this.name = name;
        this.createdAt = createdAt;
        this.user = user;
        this.count = count;
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
