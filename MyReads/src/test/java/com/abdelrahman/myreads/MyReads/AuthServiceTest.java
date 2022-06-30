package com.abdelrahman.myreads.MyReads;

import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.SignUpRequest;
import com.abdelrahman.myreads.MyReads.payload.UserProfile;
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
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    AuthService authService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JwtTokenProvider tokenProvider;

    @MockBean
    ModelMapper mapper;

    @MockBean
    UserService userService;

    @MockBean
    ShelfService shelfService;

    @MockBean
    EmailSenderService emailSenderService;

    @MockBean
    ConfirmationTokenRepository confirmationTokenRepository;

    @MockBean
    PasswordVerificationCodeRepository verificationCodeRepository;


    @Test
    @DisplayName("Sign up")
    public void singUp(){
        UserProfile userProfile = new UserProfile("Azz", "Ibrahim", "azz@gmail.com", "azz", null, null);
       SignUpRequest signUpRequest = new SignUpRequest("Azz", "Ibrahim", "azz", "azz@gmail.com", "123456", "male", LocalDate.of(2009, 3, 12), null, null);
        User user = new User("Azz", "Ibrahim", "azz", "azz@gmail.com", "123456",null, null, LocalDateTime.now(), UtilsMethods.getEnumGender("male"), 22, null, null);

        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        UserProfile u = authService.signUp(signUpRequest);
        assertEquals(u, userProfile);
    }
}
