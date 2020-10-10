package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.security.CurrentUser;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.UserRatedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rate")
public class RatingController {

    @Autowired
    UserRatedBookService userRatedBookService;

    @PostMapping("/{bookId}")
    public ResponseEntity<?> rateBook(@PathVariable final Long bookId, @RequestParam(name = "star-value") final Integer statValue, @CurrentUser UserPrincipal currentUser){

        userRatedBookService.rateBook(currentUser, bookId, statValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
