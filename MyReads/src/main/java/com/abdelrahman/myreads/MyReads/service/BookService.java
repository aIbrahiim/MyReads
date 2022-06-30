package com.abdelrahman.myreads.MyReads.service;

import com.abdelrahman.myreads.MyReads.dto.BookDTO;

public interface BookService {
    BookDTO findById(Long id);
}
