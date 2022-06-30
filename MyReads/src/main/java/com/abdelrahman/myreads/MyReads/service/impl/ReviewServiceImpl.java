package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.controller.UserController;
import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.dto.ThreadDTO;
import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.model.Review;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.PagedResponse;
import com.abdelrahman.myreads.MyReads.payload.ReviewRequest;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.repository.ReviewRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.*;
import static com.abdelrahman.myreads.MyReads.util.AppConstants.ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public ReviewDTO addReview(ReviewRequest reviewRequest, Long bookId, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, bookId));

        System.out.println(reviewRequest.getParentId());
        Review review = new Review();
        review = reviewRepository.save(review);

        ReviewDTO reviewDTO = mapper.map(review, ReviewDTO.class);
        return reviewDTO;

    }

    BiPredicate<ReviewDTO, ReviewDTO> checkParent = (p1, p2) ->{
        return p1.getParentId() == p2.getId();
    };

    @Override
    public PagedResponse<ThreadDTO> getReviewsOfBook(Long bookId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Review> reviews = null;
        List<Review> content = reviews.getNumberOfElements() == 0 ? Collections.emptyList() : reviews.getContent();
        List<ReviewDTO> contentDTO = new ArrayList<>();

        for(int i=0; i<content.size(); ++i){
            ReviewDTO reviewDTO = mapper.map(content.get(i), ReviewDTO.class);
            Link selfLink = linkTo(methodOn(UserController.class)
                    .getUserProfile(content.get(i).getUser().getId())).withSelfRel();
            reviewDTO.getUser().add(selfLink);
            contentDTO.add(reviewDTO);
        }

        List<ThreadDTO> threadReview = new ArrayList<>();

        contentDTO.stream().parallel().forEach(
                review -> {
                    ThreadDTO threadDTO = new ThreadDTO();
                    threadDTO.setReview(review);
                    List<ReviewDTO> subReviewList = contentDTO.stream().filter( subReview -> checkParent.test(subReview, review)).collect(Collectors.toList());
                    threadDTO.setSubReviews(subReviewList);
                    threadReview.add(threadDTO);
                });

        return new PagedResponse<>(threadReview, reviews.getNumber(), reviews.getSize(), reviews.getSize(),
                reviews.getTotalPages(), reviews.isLast());
    }
}
