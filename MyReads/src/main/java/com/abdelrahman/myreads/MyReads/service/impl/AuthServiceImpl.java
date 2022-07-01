package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.exception.ApiError;
import com.abdelrahman.myreads.MyReads.exception.AppException;
import com.abdelrahman.myreads.MyReads.exception.BadRequestException;
import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.model.*;
import com.abdelrahman.myreads.MyReads.payload.*;
import com.abdelrahman.myreads.MyReads.repository.ConfirmationTokenRepository;
import com.abdelrahman.myreads.MyReads.repository.PasswordVerificationCodeRepository;
import com.abdelrahman.myreads.MyReads.repository.RoleRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.JwtTokenProvider;
import com.abdelrahman.myreads.MyReads.service.AuthService;
import com.abdelrahman.myreads.MyReads.service.EmailSenderService;
import com.abdelrahman.myreads.MyReads.service.ShelfService;
import com.abdelrahman.myreads.MyReads.service.UserService;
import com.abdelrahman.myreads.MyReads.util.UtilsMethods;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    ShelfService shelfService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    PasswordVerificationCodeRepository verificationCodeRepository;


    @Override
    public JwtAuthenticationResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public UserProfile signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new AppException(new ApiError(HttpStatus.BAD_REQUEST, "Username is already taken"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AppException(new ApiError(HttpStatus.BAD_REQUEST, "Email is already taken"));
        }

        String firstName = signUpRequest.getFirstName().toLowerCase();

        String lastName = signUpRequest.getLastName().toLowerCase();

        String username = signUpRequest.getUsername().toLowerCase();

        String email = signUpRequest.getEmail().toLowerCase();

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        String website = signUpRequest.getWebsite();

        String bio = signUpRequest.getBio();

        LocalDateTime joinedAt = LocalDateTime.now();

        LocalDate birthDay = signUpRequest.getBirth();

        String gender = signUpRequest.getGender().toLowerCase();

        Gender genderValue = UtilsMethods.getEnumGender(gender);

        Period period = Period.between(birthDay, LocalDate.now());

        User user = new User(firstName, lastName, username, email, password, bio, website, joinedAt, genderValue, period.getYears(), null, null);
        user.setEnabled(true);
    /*    Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
               .orElseThrow(() -> new AppException(new ApiError(HttpStatus.BAD_REQUEST, "User Role is not set")));
        roles.add(role);
        user.setRoles(roles);


     */


        User result = userRepository.save(user);

        //shelfService.createPredefinedShelves(result);

        UserProfile userProfile = mapper.map(user, UserProfile.class);

        return userProfile;
    }

    @Override
    public ApiResponse confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid token."));

         User user = userRepository.findByEmail(token.getUser().getEmail())
                 .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

         if(user.isEnabled()){
             throw (new AppException(new ApiError(HttpStatus.BAD_REQUEST, "Email is already activated")));
         }

        if(!verifyToken(token, user)){
            generateNewTokenAndSendNewActivationLink(user,
                    "Complete Registration!",
                    "To confirm your account, please click here : "
                    +"http://localhost:8080/api/auth/confirm-account?token=");

            return new ApiResponse(Boolean.FALSE,
                    "Expired token." +
                     " \"A new link is sent to your email.\", ",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        token.setConfirmedDateTime(LocalDateTime.now());
        token.setStatus(token.STATUS_VERIFIED);
        user.setEnabled(true);
        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenRepository.save(token);
        return new ApiResponse(Boolean.TRUE, "Email activated successfully", HttpStatus.OK);
    }

    @Override
    public ApiResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByUsernameOrEmail(forgotPasswordRequest.getUsernameOrEmail(), forgotPasswordRequest.getUsernameOrEmail())
                .orElseThrow(() -> new ResourceNotFoundException(USER, "Username or Email", forgotPasswordRequest.getUsernameOrEmail()));

        user.setEnabled(false);
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        PasswordVerificationCode verificationCode = new PasswordVerificationCode(user);
        verificationCodeRepository.save(verificationCode);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Password Reset!");
        mailMessage.setFrom("MyShelf@gmail.com");
        mailMessage.setText("Please enter the following code to reset your password:\n" + verificationCode.getCode());
        emailSenderService.sendEmail(mailMessage);

        return new ApiResponse(Boolean.TRUE, "Check your mail inbox for the reset code");
    }

    @Override
    public void sendConfirmationLink(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("MyShelf@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getToken());

        emailSenderService.sendEmail(mailMessage);

    }

    /*
    @Override
    public ApiResponse confirmReset(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByToken(confirmationToken)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid token."));

        User user = userRepository.findByEmail(token.getUser().getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));
        if(!verifyToken(token, user)){

            generateNewTokenAndSendNewActivationLink(user,
                    "Complete Password Reset!",
                    "To complete the password reset process, please click here: "
                    +"http://localhost:8080/api/auth/confirm-reset?token=");

            return new ApiResponse(Boolean.FALSE,
                    "Token expired." +
                    " \"A new link is sent to your email.\", ",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        token.setConfirmedDateTime(LocalDateTime.now());
        token.setStatus(token.STATUS_VERIFIED);
        //user.setEnabled(true);
        //userRepository.save(user);
        confirmationTokenRepository.save(token);
        return new ApiResponse(Boolean.TRUE, "Email has been confirmed", HttpStatus.OK);
    }


     */

    @Override
    public ApiResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {

        PasswordVerificationCode code = verificationCodeRepository.findByCode(resetPasswordRequest.getCode())
                .orElseThrow(()-> new ResourceNotFoundException("Invalid code."));

        User user = userRepository.findByUsernameOrEmail(resetPasswordRequest.getUsernameOrEmail(), resetPasswordRequest.getUsernameOrEmail())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        if(!code.getUser().equals(user)){
            throw new BadRequestException("Error verifying the email.");
        }
        String password = passwordEncoder.encode(resetPasswordRequest.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        verificationCodeRepository.delete(code);
        return new ApiResponse(Boolean.TRUE, "Password successfully reset. You can now log in with the new credentials.", HttpStatus.OK);
    }

    private void generateNewTokenAndSendNewActivationLink(User user, String subject, String text) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setFrom("MyShelf@gmail.com");
        mailMessage.setText(text + confirmationToken.getToken());

    }

    private Boolean verifyToken(ConfirmationToken token, User user){
        if(token.getExpiredDateTime().isBefore(LocalDateTime.now()) && token.getUser().equals(user))
            return false;
        return true;
    }
}
