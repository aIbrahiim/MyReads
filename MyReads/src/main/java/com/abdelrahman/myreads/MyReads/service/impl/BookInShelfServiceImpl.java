package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.exception.BadRequestException;
import com.abdelrahman.myreads.MyReads.exception.ResourceNotFoundException;
import com.abdelrahman.myreads.MyReads.exception.UnauthorizedException;
import com.abdelrahman.myreads.MyReads.model.*;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.abdelrahman.myreads.MyReads.payload.BookToShelfRequest;
import com.abdelrahman.myreads.MyReads.repository.BookInShelfRepository;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.repository.ShelfRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.BookInShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.*;
import static com.abdelrahman.myreads.MyReads.util.AppConstants.ID;

@Service
public class BookInShelfServiceImpl implements BookInShelfService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShelfRepository shelfRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookInShelfRepository bookInShelfRepository;

    @Override
    public Boolean addBookToShelf(UserPrincipal currentUser, BookToShelfRequest bookShelfRequest) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Shelf shelf = shelfRepository.findById(bookShelfRequest.getShelfId())
                .orElseThrow(() -> new ResourceNotFoundException(SHELF, ID, bookShelfRequest.getShelfId()));

        Book book = bookRepository.findById(bookShelfRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, bookShelfRequest.getBookId()));

        if(!user.equals(shelf.getUser())){
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to add book to this shelf");
            throw new UnauthorizedException(apiResponse);
        }
        Boolean result = bookInShelfRepository.existsById(new BookShelfId(user, shelf, book));

        if(result){
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Book is already at this shelf");
            throw new BadRequestException(apiResponse);
        }

        LocalDateTime addedAt = LocalDateTime.now();
        BookInShelf bookInShelf = new BookInShelf(new BookShelfId(user, shelf, book), addedAt);
        bookInShelfRepository.save(bookInShelf);
        shelfRepository.updateCount(shelf.getId(), shelf.getCount()+1);
        return true;

    }



    @Override
    public Boolean removeBookFromShelf(UserPrincipal currentUser, Long bookId, Long shelfId) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new ResourceNotFoundException(SHELF, ID, shelfId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, bookId));

        BookShelfId bookShelfId = new BookShelfId(user, shelf, book);
        Boolean check = bookInShelfRepository.existsById(bookShelfId);

        if(!check){
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Book cannot be deleted.");
            throw new BadRequestException(apiResponse);
        }

        bookInShelfRepository.deleteById(bookShelfId);
        return true;

    }


}
