package com.abdelrahman.myreads.MyReads.controller;

import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.dto.ThreadDTO;
import com.abdelrahman.myreads.MyReads.model.Review;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.abdelrahman.myreads.MyReads.payload.PagedResponse;
import com.abdelrahman.myreads.MyReads.payload.ReviewRequest;
import com.abdelrahman.myreads.MyReads.security.CurrentUser;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.ReviewService;
import com.abdelrahman.myreads.MyReads.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewRequest reviewRequest, @PathVariable(name = "bookId") Long bookId, @CurrentUser UserPrincipal currentUser){
        ReviewDTO reviewDTO = reviewService.addReview(reviewRequest , bookId, currentUser);
        return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable(name = "bookId") Long bookId,
                                                  @PathVariable(name = "id") Long id,
            @RequestBody ReviewRequest reviewRequest,  @CurrentUser UserPrincipal currentUser){
        ReviewDTO reviewDTO = reviewService.updateReview(id, bookId, reviewRequest, currentUser);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable(name = "bookId") Long bookId,
                                                    @PathVariable(name = "id") Long id,
                                                   @CurrentUser UserPrincipal currentUser){
        ApiResponse response = reviewService.deleteReview(id, bookId, currentUser);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping
    public ResponseEntity<PagedResponse> getReviewsOfBook(@PathVariable(name = "bookId") Long bookId,
                                                                     @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page, Integer size){
        PagedResponse reviews = reviewService.getReviewsOfBook(bookId, page);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
