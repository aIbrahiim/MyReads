package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.security.UserPrincipal;

public interface UserRatedBookService {

    public void rateBook(UserPrincipal currentUser, Long bookId, Integer rate);
}
