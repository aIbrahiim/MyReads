package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.BookInShelf;
import com.abdelrahman.myreads.MyReads.model.BookShelfId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookInShelfRepository extends JpaRepository<BookInShelf, BookShelfId> {

    @Modifying
    @Transactional
    @Query(value = "delete from book_in_shelf b where b.shelf_id = :shelf_id", nativeQuery=true)
    void deleteByShelfId(@Param("shelf_id") Long shelf_id);


    @Modifying
    @Transactional
    @Query(value = "delete from book_in_shelf b where b.book_id = :book_id", nativeQuery=true)
    void deleteByBookId(@Param("book_id") Long book_id);


}
