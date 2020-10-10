package com.abdelrahman.myreads.MyReads.service.impl;

import com.abdelrahman.myreads.MyReads.dto.BookDTO;
import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.repository.BookRepository;
import com.abdelrahman.myreads.MyReads.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public BookDTO findById(Long id) {

        Optional<Book> book = bookRepository.findById(id);
        BookDTO bookDTO = mapper.map(book.get(), BookDTO.class);
        return bookDTO;

    }
}
