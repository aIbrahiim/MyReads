package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.dto.BookDTO;
import com.abdelrahman.myreads.MyReads.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book/show")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('User')")
    public ResponseEntity<BookDTO> getById(@PathVariable(name = "id") Long id){
        BookDTO bookDTO = bookService.findById(id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);

    }
}
