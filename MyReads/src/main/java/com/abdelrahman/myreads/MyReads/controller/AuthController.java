package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.payload.*;
import com.abdelrahman.myreads.MyReads.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myreads/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody LoginRequest loginRequest) {
       JwtAuthenticationResponse jwtResponse = authService.signIn(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserProfile> signup(@RequestBody SignUpRequest signUpRequest) {

       UserProfile userProfile = authService.signUp(signUpRequest);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }


}
