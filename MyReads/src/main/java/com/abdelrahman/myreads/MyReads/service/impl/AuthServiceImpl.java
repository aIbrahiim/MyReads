package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.exception.ApiError;
import com.abdelrahman.myreads.MyReads.exception.AppException;
import com.abdelrahman.myreads.MyReads.model.Gender;
import com.abdelrahman.myreads.MyReads.model.Role;
import com.abdelrahman.myreads.MyReads.model.RoleName;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.JwtAuthenticationResponse;
import com.abdelrahman.myreads.MyReads.payload.LoginRequest;
import com.abdelrahman.myreads.MyReads.payload.SignUpRequest;
import com.abdelrahman.myreads.MyReads.payload.UserProfile;
import com.abdelrahman.myreads.MyReads.repository.RoleRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.JwtTokenProvider;
import com.abdelrahman.myreads.MyReads.service.AuthService;
import com.abdelrahman.myreads.MyReads.service.ShelfService;
import com.abdelrahman.myreads.MyReads.service.UserService;
import com.abdelrahman.myreads.MyReads.util.UtilsMethods;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

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
            throw new ApiError(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "Email is already taken");
        }

        String firstName = signUpRequest.getFirstName().toLowerCase();

        String lastName = signUpRequest.getLastName().toLowerCase();

        String username = signUpRequest.getUsername().toLowerCase();

        String email = signUpRequest.getEmail().toLowerCase();

        String password = passwordEncoder.encode(signUpRequest.getPassword());

        String website = signUpRequest.getWebsite();

        String bio = signUpRequest.getBio();

        LocalDateTime joinedAt = LocalDateTime.now();

        LocalDate birthDay = signUpRequest.getBirthDay();

        String gender = signUpRequest.getGender().toLowerCase();

        Gender genderValue = UtilsMethods.getEnumGender(gender);

        Period period = Period.between(birthDay, LocalDate.now());

        User user = new User(firstName, lastName, username, email, password, bio, website, joinedAt, genderValue, period.getYears());

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException(new ApiError(HttpStatus.BAD_REQUEST, "User Role is not set")));
        roles.add(role);
        user.setRoles(roles);


        User result = userRepository.save(user);

        shelfService.createPredefinedShelves(result);

       UserProfile userProfile = mapper.map(user, UserProfile.class);
        return userProfile;
    }
}
