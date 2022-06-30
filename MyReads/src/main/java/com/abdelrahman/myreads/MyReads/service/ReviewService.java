package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.dto.ThreadDTO;
import com.abdelrahman.myreads.MyReads.model.Review;
import com.abdelrahman.myreads.MyReads.payload.PagedResponse;
import com.abdelrahman.myreads.MyReads.payload.ReviewRequest;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    public ReviewDTO addReview(ReviewRequest reviewRequest, Long bookId, UserPrincipal currentUser);

    public PagedResponse<ThreadDTO> getReviewsOfBook(Long bookId, int page, int size);
}
