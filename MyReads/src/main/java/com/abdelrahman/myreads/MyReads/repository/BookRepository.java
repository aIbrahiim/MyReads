package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
