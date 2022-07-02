package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.dto.ThreadDTO;
import com.abdelrahman.myreads.MyReads.model.Review;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.abdelrahman.myreads.MyReads.payload.PagedResponse;
import com.abdelrahman.myreads.MyReads.payload.ReviewRequest;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    public ReviewDTO addReview(ReviewRequest reviewRequest, Long bookId, UserPrincipal currentUser);
    public ReviewDTO updateReview(Long reviewId, Long bookId, ReviewRequest reviewRequest, UserPrincipal currentUser);
    public ApiResponse deleteReview(Long reviewId, Long bookId, UserPrincipal currentUser);
    public PagedResponse getReviewsOfBook(Long bookId, int page);
}
