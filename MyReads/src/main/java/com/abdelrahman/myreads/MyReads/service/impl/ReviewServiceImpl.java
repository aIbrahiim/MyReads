package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.controller.UserController;
import com.abdelrahman.myreads.MyReads.dto.ReviewDTO;
import com.abdelrahman.myreads.MyReads.exception.BadRequestException;
import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.model.Review;
import com.abdelrahman.myreads.MyReads.model.RoleName;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
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

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.*;
import static com.abdelrahman.myreads.MyReads.util.AppConstants.ID;
import static com.abdelrahman.myreads.MyReads.util.AppConstants.REVIEWS_DEFAULT_PAGE_SIZE;
import static com.abdelrahman.myreads.MyReads.util.UtilsMethods.toReviewDTO;
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

        Review review =  new Review();
        review.setUser(user);
        review.setBook(book);
        Long parentId = reviewRequest.getParentId();

        if(parentId!=null && !reviewRepository.existsById(parentId))
            parentId = null;
        review.setParentId(reviewRequest.getParentId());
        review.setBody(reviewRequest.getBody());
        review.setRating(reviewRequest.getRating());
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);

        ReviewDTO reviewDTO = mapper.map(review, ReviewDTO.class);
        reviewDTO.setFullName(user.getFirstName() + " " + user.getLastName());
        Link userLink = linkTo(methodOn(UserController.class).getUserProfile(user.getId())).withSelfRel();
        reviewDTO.setUserLink(userLink);
        return reviewDTO;
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, Long bookId, ReviewRequest reviewRequest, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, bookId));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(REVIEW, ID, 1L));

        if(!review.getBook().equals(bookId) || !review.getParentId().equals(reviewRequest.getParentId()))
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Can't' update this review, values doesn't match", HttpStatus.BAD_REQUEST));
        if (review.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            review.setBody(reviewRequest.getBody());
            ReviewDTO reviewDTO = mapper.map(reviewRepository.save(review), ReviewDTO.class);
        }

        throw new BadRequestException(new ApiResponse(Boolean.FALSE, "You don't have permission to update this review", HttpStatus.UNAUTHORIZED));    }

    @Override
    public ApiResponse deleteReview(Long reviewId, Long bookId, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, bookId));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(REVIEW, ID, reviewId));
        if(!review.getBook().getId().equals(bookId)){
            throw new BadRequestException(new ApiResponse(Boolean.FALSE, "Can't' update this review, values doesn't match", HttpStatus.BAD_REQUEST));
        }
        if (review.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            reviewRepository.deleteById(reviewId);
            reviewRepository.deleteReviews(reviewId);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted the review", HttpStatus.OK);
        }
        throw new BadRequestException(new ApiResponse(Boolean.FALSE, "You don't have permission to delete this review", HttpStatus.UNAUTHORIZED));    }





   /* BiPredicate<ReviewDTO, ReviewDTO> checkParent = (p1, p2) ->{
        return p1.getParentId() == p2.getId();
    };

    */

    @Override
    public PagedResponse getReviewsOfBook(Long bookId, int page) {

        Pageable pageable = PageRequest.of(page, REVIEWS_DEFAULT_PAGE_SIZE, Sort.Direction.DESC, CREATED_AT);
        Page<Review> reviews = reviewRepository.findByBookId(bookId, pageable);
        List<Review> content = reviews.getNumberOfElements() == 0 ? Collections.emptyList() : reviews.getContent();

        Map<ReviewDTO, List<ReviewDTO>> ThreadReview = new HashMap<>();

        for(int i=0; i<content.size(); ++i){
          Review review = content.get(i);
            if(review.getParentId() == null){
                ReviewDTO reviewDTO = toReviewDTO(review);
                ThreadReview.put(reviewDTO, new ArrayList<>());
            }
        }

        for(int i=0; i<content.size(); ++i){
            Review review = content.get(i);
            if(review.getParentId() == null){
                continue;
            }
            Optional<Review> reviewParent = reviewRepository.findById(review.getParentId());
            ReviewDTO reviewParentDTO = toReviewDTO(reviewParent.get());
            List<ReviewDTO> temp = ThreadReview.get(reviewParentDTO);
            System.out.println(reviewParentDTO.getId());
            ReviewDTO subReviewDTO = toReviewDTO(review);
            temp.add(subReviewDTO);
            ThreadReview.put(reviewParentDTO, temp);
        }
        return new PagedResponse(ThreadReview, reviews.getNumber(), reviews.getSize(),
        reviews.getTotalElements(), reviews.getTotalPages(), reviews.isLast());



        //method two
      /*
       for(int i=0; i<content.size(); ++i){
            ReviewDTO reviewDTO = mapper.map(content.get(i), ReviewDTO.class);
            Link selfLink = linkTo(methodOn(UserController.class)
                    .getUserProfile(content.get(i).getUser().getId())).withSelfRel();
            reviewDTO.setSelfLink(selfLink);
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
                reviews.getTotalPages(), reviews.isLast());  */
    }
}
