package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.payload.JwtAuthenticationResponse;
import com.abdelrahman.myreads.MyReads.payload.LoginRequest;
import com.abdelrahman.myreads.MyReads.payload.SignUpRequest;
import com.abdelrahman.myreads.MyReads.payload.UserProfile;

public interface AuthService {

    public JwtAuthenticationResponse signIn(LoginRequest loginRequest);

    public UserProfile signUp(SignUpRequest signUpRequest);
}
