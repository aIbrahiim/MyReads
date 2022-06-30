package com.abdelrahman.myreads.MyReads.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    private String code;
    private String usernameOrEmail;
    private String password;
}
