package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.UserProfile;
import com.abdelrahman.myreads.MyReads.repository.RoleRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.service.ShelfService;
import com.abdelrahman.myreads.MyReads.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ShelfService shelfService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper mapper;

    @Override
    public UserProfile getUserProfile(Long id) {
        User user =  userRepository.getOne(id);
        UserProfile userProfile = mapper.map(user, UserProfile.class);
        return userProfile;
    }
}
