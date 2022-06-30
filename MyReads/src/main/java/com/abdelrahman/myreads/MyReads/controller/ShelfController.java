package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.model.Shelf;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.abdelrahman.myreads.MyReads.payload.BookToShelfRequest;
import com.abdelrahman.myreads.MyReads.payload.ShelfRequest;
import com.abdelrahman.myreads.MyReads.security.CurrentUser;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelves")
public class ShelfController {

    @Autowired
    ShelfService shelfService;



    @PostMapping
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApiResponse> createShelf(@RequestBody final ShelfRequest shelfRequest, @CurrentUser UserPrincipal currentUser){
        Boolean result = shelfService.createShelf(currentUser, shelfRequest);
        ApiResponse apiResponse = new ApiResponse(result, "Shelf created");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
    @PostMapping(value = "/add-book-to-shelf")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> addBookToShelf(@RequestBody BookToShelfRequest bookToShelfRequest, @CurrentUser UserPrincipal currentUser){

        //Boolean result = bookInShelfService.addBookToShelf(currentUser, bookToShelfRequest);
        ApiResponse apiResponse = new ApiResponse(true, "Book added to shelf");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{shelfId}/remove-shelf")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApiResponse> deleteShelf(@PathVariable final  Long shelfId, @CurrentUser UserPrincipal currentUser){
        Boolean result = shelfService.deleteShelf(currentUser, shelfId);
        ApiResponse apiResponse = new ApiResponse(result, "Shelf deleted.");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{shelfId}")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<ApiResponse> deleteBookFromShelf(@PathVariable final  Long shelfId, @RequestParam(name = "bookId") final Long bookId, @CurrentUser UserPrincipal currentUser){
        //Boolean result = bookInShelfService.removeBookFromShelf(currentUser, shelfId, bookId);
        ApiResponse apiResponse = new ApiResponse(true, "Shelf deleted.");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('User')")
    public List<Shelf> getUserShelves(@PathVariable final String username){
        List<Shelf> res = shelfService.getShelvesOfUser(username);
        return res;
    }

}
