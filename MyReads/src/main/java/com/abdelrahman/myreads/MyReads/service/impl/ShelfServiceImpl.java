package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.exception.*;
import com.abdelrahman.myreads.MyReads.model.*;
import com.abdelrahman.myreads.MyReads.payload.ApiResponse;
import com.abdelrahman.myreads.MyReads.payload.ShelfRequest;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.repository.ShelfRepository;
import com.abdelrahman.myreads.MyReads.repository.UserRepository;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;
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



    @Override
    public void createPredefinedShelves(User user) {
        User curUser = null;;
        LocalDateTime addedAt = LocalDateTime.now();
        Shelf read = new Shelf(true, "read", curUser);
        Shelf toRead = new Shelf(true, "to read", curUser);
        Shelf currentlyRead = new Shelf(true, "currently read", curUser);

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
        Optional<Shelf> checkShelf =  null;

        if(checkShelf.isPresent() && checkShelf.get().getUser().equals(user)){
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Shelf already exists");
            throw new BadRequestException(apiResponse);
        }

        LocalDateTime createdAt = LocalDateTime.now();
        Shelf shelf = new Shelf(Boolean.FALSE, shelfName, user);
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

        shelfRepository.delete(shelf);
        return true;
    }
}
