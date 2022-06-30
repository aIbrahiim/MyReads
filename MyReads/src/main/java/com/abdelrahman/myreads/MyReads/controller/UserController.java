package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.payload.UserProfile;
import com.abdelrahman.myreads.MyReads.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/show")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable(value = "id") Long id){
        UserProfile userProfile = userService.getUserProfile(id);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
