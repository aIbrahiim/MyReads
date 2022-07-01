package com.abdelrahman.myreads.MyReads.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public abstract class Person {



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


}
