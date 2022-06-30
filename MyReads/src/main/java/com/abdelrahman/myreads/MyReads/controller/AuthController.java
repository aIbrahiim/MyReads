package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.model.ConfirmationToken;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.*;
import com.abdelrahman.myreads.MyReads.repository.ConfirmationTokenRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.service.AuthService;
import com.abdelrahman.myreads.MyReads.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.ID;
import static com.abdelrahman.myreads.MyReads.util.AppConstants.USER;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;


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

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ApiResponse> confirmUserAccount(@RequestParam("token") String confirmationToken)
    {
      ApiResponse apiResponse = authService.confirmEmail(confirmationToken);
      return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        ApiResponse apiResponse = authService.forgotPassword(forgotPasswordRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /*
    @RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ApiResponse> confirmReset(@RequestParam("token") String confirmationToken){
        ApiResponse apiResponse = authService.confirmReset(confirmationToken);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

     */

    @PostMapping("reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        ApiResponse apiResponse = authService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
