package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class Home {

  @GetMapping
  //@PreAuthorize("hasRole('User')")
  public String home(){
     return "welcome!";
  }
   }
