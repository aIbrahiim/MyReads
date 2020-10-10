package com.abdelrahman.myreads.MyReads.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 4, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 40)
    private String lastName;

    @NotBlank
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private String gender;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate birthDay;

    private String website;

    private String bio;



}
