package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.model.Shelf;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.payload.ShelfRequest;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;

import java.util.List;

public interface ShelfService {

    public void createPredefinedShelves(User user);

    public List<Shelf> getShelvesOfUser(String username);

    public boolean createShelf(UserPrincipal currentUser, ShelfRequest shelfRequest);

    public boolean deleteShelf(UserPrincipal currentUser, Long id);
}