package com.abdelrahman.myreads.MyReads.repository;

import com.abdelrahman.myreads.MyReads.model.Book;
import com.abdelrahman.myreads.MyReads.model.User;
import com.abdelrahman.myreads.MyReads.model.UserRatedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRatedBookRepository extends JpaRepository<UserRatedBook, Long> {


    @Query(value = "select exists(select * from user_rated_book r where r.user_id = :user_id and r.book_id = :book_id and r.rate = :rate)", nativeQuery = true)
    Boolean checkRateValue(@Param("book_id") Long bookId, @Param("user_id") Long userId, @Param("rate") Integer rate);

    @Query(value = "select exists(select * from user_rated_book r where r.user_id = :user_id and r.book_id = :book_id and r.rate <> :rate)", nativeQuery = true)
    Boolean checkRateNotExists(@Param("book_id") Long bookId, @Param("user_id") Long userId, @Param("rate") Integer rate);

    @Transactional
    @Modifying
    @Query(value = "delete from user_rated_book b where b.user_id = :user_id and b.book_id = :book_id and b.rate = :rate", nativeQuery = true)
    void deleteRate(@Param("user_id") Long userId, @Param("book_id") Long bookId, @Param("rate") Integer rate);
}
