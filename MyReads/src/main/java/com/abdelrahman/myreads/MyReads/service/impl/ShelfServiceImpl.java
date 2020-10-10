package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.exception.*;
import com.abdelrahman.myreads.MyReads.model.*;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.abdelrahman.myreads.MyReads.payload.ShelfRequest;
import com.abdelrahman.myreads.MyReads.repository.BookInShelfRepository;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.repository.ShelfRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
import com.abdelrahman.myreads.MyReads.service.BookInShelfService;
import com.abdelrahman.myreads.MyReads.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.abdelrahman.myreads.MyReads.util.AppConstants.*;

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShelfRepository shelfRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookInShelfRepository bookInShelfRepository;

    @Autowired
    BookInShelfService bookInShelfService;

    @Override
    public void createPredefinedShelves(User user) {
        //User user = userRepository.getUserByName(currentUser.getUsername());
        LocalDateTime addedAt = LocalDateTime.now();
        Shelf read = new Shelf(true, "read", addedAt,user, 0);
        Shelf toRead = new Shelf(true, "to read", addedAt,user, 0);
        Shelf currentlyRead = new Shelf(true, "currently read",  addedAt,user, 0);

        shelfRepository.save(read);
        shelfRepository.save(toRead);
        shelfRepository.save(currentlyRead);


    }

    @Override
    public List<Shelf> getShelvesOfUser(String username) {
        User user = userRepository.getUserByName(username);
        List<Shelf> shelves = shelfRepository.findAllByUser(user);
        return shelves;
    }



    @Override
    public boolean createShelf(UserPrincipal currentUser, ShelfRequest shelfRequest) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        String shelfName = shelfRequest.getShelfName().toLowerCase();
        System.out.println(shelfName);
        Optional<Shelf> checkShelf = shelfRepository.findByName(shelfName);

        if(checkShelf.isPresent() && checkShelf.get().getUser().equals(user)){
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Shelf already exists");
            throw new BadRequestException(apiResponse);
        }

        LocalDateTime createdAt = LocalDateTime.now();
        Shelf shelf = new Shelf(Boolean.FALSE, shelfName, createdAt, user, 0);
        shelfRepository.save(shelf);
        return true;
    }

    @Override
    public boolean deleteShelf(UserPrincipal currentUser, Long id) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, 1L));

        Shelf shelf = shelfRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SHELF, String.valueOf(id), 1L));

        if(!shelf.getUser().equals(user)){
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to add book to this shelf");
            throw new UnauthorizedException(apiResponse);
        }
        bookInShelfRepository.deleteByShelfId(shelf.getId());
        shelfRepository.delete(shelf);
        return true;
    }
}
