package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.payload.BookToShelfRequest;
import com.abdelrahman.myreads.MyReads.security.UserPrincipal;

public interface BookInShelfService {
    public Boolean addBookToShelf(UserPrincipal currentUser, BookToShelfRequest bookToShelfRequest);

    public Boolean removeBookFromShelf(UserPrincipal currentUser, Long bookId, Long shelfId);


}
