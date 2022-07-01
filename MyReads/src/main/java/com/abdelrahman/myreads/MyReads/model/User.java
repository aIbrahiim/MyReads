package com.abdelrahman.myreads.MyReads.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;
    @NotBlank
    @Size(max = 40)
    protected String firstName;

    @NotBlank
    @Size(max = 40)
    protected String lastName;

    @JsonFormat(pattern="yyyy-MM-dd")
    protected LocalDate birth;

    protected Integer age;

    @Enumerated(EnumType.STRING)
    protected Gender gender;

    @Size(min = 0, max = 200)
    protected String bio;

    protected String city;

    protected String country;
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @JsonIgnore
    private String password;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinedAt;



    private String website;

    private boolean isEnabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName, String username, String email, String password, String bio, String website, LocalDateTime joinedAt, Gender gender, int age, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.website = website;
        this.joinedAt = joinedAt;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
      return Objects.hash(username, email);
    }
}
