package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.dto.BookDTO;
import com.abdelrahman.myreads.MyReads.dto.ThreadDTO;
import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.payload.PagedResponse;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.service.BookService;
import com.abdelrahman.myreads.MyReads.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.BOOK;
import static com.abdelrahman.myreads.MyReads.util.AppConstants.ID;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ModelMapper mapper;

    @Override
    public BookDTO findById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, id));
        Double rating = bookRepository.groupByRating(id);
        book.setRating(rating);
        BookDTO bookDTO = mapper.map(book, BookDTO.class);
        PagedResponse reviews = reviewService.getReviewsOfBook(book.getId(), 0);
        bookDTO.setReviews(reviews);
        return bookDTO;

    }
}
