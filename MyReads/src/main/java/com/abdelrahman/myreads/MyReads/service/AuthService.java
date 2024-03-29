package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.*;

public interface AuthService {

    public JwtAuthenticationResponse signIn(LoginRequest loginRequest);

    public UserProfile signUp(SignUpRequest signUpRequest);

    public ApiResponse confirmEmail(String confirmationToken);

    public ApiResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    public void sendConfirmationLink(User user);

    //public ApiResponse confirmReset(String token);

    public ApiResponse resetPassword(ResetPasswordRequest resetPasswordRequest);
}
